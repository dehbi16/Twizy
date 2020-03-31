close all
clear all

%%
pions = imread('pions.jpg');
I = rgb2gray(pions);
BW = edge(I,'Canny');
[centers, radii, metric]=imfindcircles(BW,[60 120],'Sensitivity',0.94);
%%imshow(pions)
centersStrong5 = centers(1:end,:); 
radiiStrong5 = radii(1:end);
metricStrong5 = metric(1:end);
%%viscircles(centersStrong5, radiiStrong5,'EdgeColor','b');

%%
stan1 = imread('StanislasFace.jpg');
stan2 = imread('StanislasFace2.jpg');
imshow(stan1);
I = rgb2gray(stan1);
corners = detectSURFFeatures  (I);
[features, valid_corners] = extractFeatures(I, corners);
hold on;
plot(corners.selectStrongest(50));
plot(valid_corners);
hold off;

figure(2);
imshow(stan2);
I2 = rgb2gray(stan2);
corners2 = detectSURFFeatures  (I2);
[features2, valid_corners2] = extractFeatures(I2, corners2);
hold on;
plot(corners2.selectStrongest(50));
plot(valid_corners2);

hold off;

indexPairs = matchFeatures(features,features2);
matchedPoints1 = valid_corners(indexPairs(:,1),:);
matchedPoints2 = valid_corners2(indexPairs(:,2),:);
% figure; showMatchedFeatures(I,I2,matchedPoints1,matchedPoints2);
figure; ax = axes;
showMatchedFeatures(I,I2,matchedPoints1,matchedPoints2,'montage','Parent',ax);