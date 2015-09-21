set output '.ps' 
set terminal postscript 
set title "" 
set xlabel "" 
set ylabel "" 
set zlabel "" 
unset logscale x 
unset logscale y 
unset logscale z 
plot [:] [:] [:] 
set output '| ' 
pause -1 'Press ENTER to continue...' 
