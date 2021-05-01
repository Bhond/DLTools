clear all
close all


[y, FS] = audioread('a440.wav');
toto = y(1:2048);
p = fft(toto);
a = abs(p);
x = 1:2048;

[p,q,r,pwr] = spectrogram(y,2048,20,[],FS,'yaxis');