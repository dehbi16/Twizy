close all
clear all

%% charger image
% boat=imread('boat.png'); 
% figure(1);
% imshow(boat);
% imhist(boat)
% seuil = 0.4;
% level = graythresh(boat);
% indsup = boat > seuil;
% indinf = boat < seuil;
% IboatNB = zeros(size(boat));
% IboatNB(indsup) = 1;
% 
% BW = imbinarize(boat,seuil);
% figure(2);
% imshow(BW)

%%
pion = imread('pions.jpg');
% figure(3);
% imshow(pion);

imhsv = rgb2hsv(pion);
% figure(4);
% imhist(imhsv)


seuiljaune = find((imhsv(:,:,1)>.12) & (imhsv(:,:,1)<.17));
seuilrouge = find((imhsv(:,:,1)>.97) & (imhsv(:,:,1)<.99));
pions1 = pion(:,:,1);
pions2 = pion(:,:,2);
pions3 = pion(:,:,3);

Ipionsseuil1 = zeros(size(pion,1));
Ipionsseuil2 = zeros(size(pion,1));
Ipionsseuil3 = zeros(size(pion,1));

Ipionsseuil1(seuilrouge) = pions1(seuilrouge);
Ipionsseuil2(seuilrouge) = pions2(seuilrouge);
Ipionsseuil3(seuilrouge) = pions3(seuilrouge);

Ipionsseuil(:,:,1) = Ipionsseuil1;
Ipionsseuil(:,:,2) = Ipionsseuil2;
Ipionsseuil(:,:,3) = Ipionsseuil3;

% figure(6);
% imshow(Ipionsseuil);

%%

Se3 = strel('disk',2);
Ipionsseuil = imerode(Ipionsseuil,Se3);

% figure(7);
% imshow(Ipionsseuil);
Se3 = strel('disk',3);
Ipionsseuil = imdilate(Ipionsseuil,Se3);

% figure(8);
% imshow(Ipionsseuil);

%%
elaine = imread('elaine.jpg');
BW = edge(elaine,'approxcanny');
figure(9);
imshow(elaine)
figure(10);
imshow(BW)

imshow(BW)
