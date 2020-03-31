close all 

%% charger image
boat=imread('boat.png'); 
% imshow(boat);
elaine=imread('elaine.jpg'); 
% imshow(elaine);

%% afficher log phase
dbo=im2double(boat);
del=im2double(elaine);
fboa=fftshift(fft2(dbo));
fel=fftshift(fft2(del));

modboa=log(abs(fboa));
model=log(abs(fel));
% figure(1)
% imshow(modboa/max(max(modboa)));
% figure(2)
% imshow(model/max(max(model)));

pboa=angle(fboa);
pel=angle(fel);

% figure(3)
% imshow(pboa);
% figure(4)
% imshow(pel);

%% fft inverse
fboa=abs(fboa).*exp(i*pel);
fel=abs(fel).*exp(i*pboa);

boat2=ifft2(fboa);
elaine2=ifft2(fel);

figure(5)
imshow(boat2);
figure(6)
imshow(elaine2);

%% ss echan
boat3=dbo(1:2:end,1:2:end);
elaine3=del(1:2:end,1:2:end);
figure(7)
imshow(boat3);
figure(8)
imshow(elaine3);

%% filtre
F=[1 2 1;2 4 2;1 2 1]/16;
boat4 = imfilter(dbo,F);
elaine4 = imfilter(del,F);
boat5 = boat4(1:2:end,1:2:end);
elaine5 = elaine4(1:2:end,1:2:end);
figure(9)
imshow(boat5);
figure(10)
imshow(elaine5);
title("ksfg");