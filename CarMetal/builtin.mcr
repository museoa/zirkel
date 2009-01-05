<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="@builtin@/3Ddode" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="t">t</Parameter>
<Objects>
<Expression name="E1" n="0" color="1" type="thick" hidden="super" showname="true" showvalue="true" bold="true" x="-7.081614349775784" y="1.0834080717488792" value="(1+sqrt(5))/2" prompt="fi">Expression &quot;(1+sqrt(5))/2&quot; à -7.08161, 1.08341 </Expression>
<Point name="t" n="1" showname="true" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(t)-windowcx)+windowcx+d(windowcx)" actx="27.874625153493675" y="(windoww/(windoww-d(windoww)))*(y(t)-windowcy)+windowcy+d(windowcy)" acty="0.9733581650193237" shape="dcross" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(t)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(t)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Point name="O" n="2" mainparameter="true" x="33.49485903558816" y="0.30428270286520764">Point à 33.49486, 0.30428</Point>
<Expression name="E2" n="3" color="1" type="thick" showname="true" showvalue="true" x="x(t)" y="y(t)+15/pixel" value="2" prompt="k" fixed="true">Expression &quot;2&quot; à 27.87463, 1.12336</Expression>
<Point name="X" n="4" mainparameter="true" x="32.509744110792894" y="0.2620342829907925">Point à 32.50974, 0.26203</Point>
<Point name="Y" n="5" mainparameter="true" x="33.66675605401952" y="0.062163616989825705">Point à 33.66676, 0.06216</Point>
<Point name="Z" n="6" mainparameter="true" x="33.49485903558816" y="1.2736089736703404">Point à 33.49486, 1.27361</Point>
<Point name="P6" n="7" hidden="super" x="x(t)+E2*(x(Z)-x(O))" actx="27.874625153493675" y="y(t)+E2*(y(Z)-y(O))" acty="2.912010706629589" shape="dcross" fixed="true">Point à &quot;x(t)+E2*(x(Z)-x(O))&quot;, &quot;y(t)+E2*(y(Z)-y(O))&quot;</Point>
<Point name="P7" n="8" hidden="super" x="x(t)+E2*(x(Y)-x(O))" actx="28.218419190356393" y="y(t)+E2*(y(Y)-y(O))" acty="0.4891199932685598" shape="dcross" fixed="true">Point à &quot;x(t)+E2*(x(Y)-x(O))&quot;, &quot;y(t)+E2*(y(Y)-y(O))&quot;</Point>
<Point name="P8" n="9" hidden="super" x="x(t)+E2*(x(X)-x(O))" actx="25.904395303903136" y="y(t)+E2*(y(X)-y(O))" acty="0.8888613252704934" shape="dcross" fixed="true">Point à &quot;x(t)+E2*(x(X)-x(O))&quot;, &quot;y(t)+E2*(y(X)-y(O))&quot;</Point>
<Point name="P9" n="10" color="3" hidden="super" showname="true" x="x(t)+cos(72)*(x(P8)-x(t))+sin(72)*(x(P7)-x(t))" actx="27.592758206167105" y="y(t)+cos(72)*(y(P8)-y(t))+sin(72)*(y(P7)-y(t))" acty="0.4867093368835428" fixed="true">Point à &quot;x(P76)+cos(72)*(x(P13)-x(P76))+sin(72)*(x(P14)-x(P76))&quot;, &quot;y(P76)+cos(72)*(y(P13)-y(P76))+sin(72)*(y(P14)-y(P76))&quot; </Point>
<Point name="P10" n="11" color="3" hidden="super" showname="true" x="x(t)+cos(144)*(x(P8)-x(t))+sin(144)*(x(P7)-x(t))" actx="29.67065164933122" y="y(t)+cos(144)*(y(P8)-y(t))+sin(144)*(y(P7)-y(t))" acty="0.7570894883949353" fixed="true">Point à &quot;x(P76)+cos(144)*(x(P13)-x(P76))+sin(144)*(x(P14)-x(P76))&quot;, &quot;y(P76)+cos(144)*(y(P13)-y(P76))+sin(144)*(y(P14)-y(P76))&quot; </Point>
<Point name="P11" n="12" color="3" hidden="super" showname="true" x="x(t)+cos(216)*(x(P8)-x(t))+sin(216)*(x(P7)-x(t))" actx="29.266497519943215" y="y(t)+cos(216)*(y(P8)-y(t))+sin(216)*(y(P7)-y(t))" acty="1.3263456002992726" fixed="true">Point à &quot;x(P76)+cos(216)*(x(P13)-x(P76))+sin(216)*(x(P14)-x(P76))&quot;, &quot;y(P76)+cos(216)*(y(P13)-y(P76))+sin(216)*(y(P14)-y(P76))&quot; </Point>
<Point name="P12" n="13" color="3" hidden="super" showname="true" x="x(t)+cos(288)*(x(P8)-x(t))+sin(288)*(x(P7)-x(t))" actx="26.938823088123698" y="y(t)+cos(288)*(y(P8)-y(t))+sin(288)*(y(P7)-y(t))" acty="1.4077850742483744" fixed="true">Point à &quot;x(P76)+cos(288)*(x(P13)-x(P76))+sin(288)*(x(P14)-x(P76))&quot;, &quot;y(P76)+cos(288)*(y(P13)-y(P76))+sin(288)*(y(P14)-y(P76))&quot; </Point>
<Point name="P13" n="14" color="1" hidden="super" showname="true" x="x(t)+E1*cos(72)*(x(P8)-x(t))+E1*sin(72)*(x(P7)-x(t))+x(P6)-x(t)" actx="27.41855485241411" y="y(t)+E1*cos(72)*(y(P8)-y(t))+E1*sin(72)*(y(P7)-y(t))+y(P6)-y(t)" acty="2.1245963621205894" fixed="true">Point à &quot;x(P76)+E1*cos(72)*(x(P13)-x(P76))+E1*sin(72)*(x(P14)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*cos(72)*(y(P13)-y(P76))+E1*sin(72)*(y(P14)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P14" n="15" color="1" hidden="super" showname="true" x="x(t)+E1*cos(144)*(x(P8)-x(t))+E1*sin(144)*(x(P7)-x(t))+x(P6)-x(t)" actx="30.78065706845419" y="y(t)+E1*cos(144)*(y(P8)-y(t))+E1*sin(144)*(y(P7)-y(t))+y(P6)-y(t)" acty="2.5620806371493687" fixed="true">Point à &quot;x(P76)+E1*cos(144)*(x(P13)-x(P76))+E1*sin(144)*(x(P14)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*cos(144)*(y(P13)-y(P76))+E1*sin(144)*(y(P14)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P15" n="16" color="1" hidden="super" showname="true" x="x(t)+E1*cos(216)*(x(P8)-x(t))+E1*sin(216)*(x(P7)-x(t))+x(P6)-x(t)" actx="30.126721950410783" y="y(t)+E1*cos(216)*(y(P8)-y(t))+E1*sin(216)*(y(P7)-y(t))+y(P6)-y(t)" acty="3.4831563745142002" fixed="true">Point à &quot;x(P76)+E1*cos(216)*(x(P13)-x(P76))+E1*sin(216)*(x(P14)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*cos(216)*(y(P13)-y(P76))+E1*sin(216)*(y(P14)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P16" n="17" color="1" hidden="super" showname="true" x="x(t)+E1*cos(288)*(x(P8)-x(t))+E1*sin(288)*(x(P7)-x(t))+x(P6)-x(t)" actx="26.3604656049827" y="y(t)+E1*cos(288)*(y(P8)-y(t))+E1*sin(288)*(y(P7)-y(t))+y(P6)-y(t)" acty="3.614928211389758" fixed="true">Point à &quot;x(P76)+E1*cos(288)*(x(P13)-x(P76))+E1*sin(288)*(x(P14)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*cos(288)*(y(P13)-y(P76))+E1*sin(288)*(y(P14)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P17" n="18" color="1" hidden="super" showname="true" x="x(t)+E1*(x(P8)-x(t))+x(P6)-x(t)" actx="24.68672629120659" y="y(t)+E1*(y(P8)-y(t))+y(P6)-y(t)" acty="2.7752919479740283" fixed="true">Point à &quot;x(P76)+E1*(x(P13)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*(y(P13)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P18" n="19" color="2" hidden="super" showname="true" xcoffset="-0.5082592121982206" ycoffset="0.1820221839562297" keepclose="true" bold="true" large="true" x="x(t)+E1*(x(P6)-x(t))" actx="27.874625153493675" y="y(t)+E1*(y(P6)-y(t))" acty="4.110163869721103" fixed="true">Point à &quot;x(P76)+E1*(x(P15)-x(P76))&quot;, &quot;y(P76)+E1*(y(P15)-y(P76))&quot; </Point>
<Midpoint name="M1" n="20" color="1" type="thick" hidden="super" showname="true" xcoffset="-0.3659466327827179" ycoffset="-0.20665765098231326" keepclose="true" bold="true" large="true" first="P18" second="P6" shape="circle">Milieu de P18 et P6</Midpoint>
<Point name="P19" n="21" color="5" type="thick" target="true" bold="true" x="x(P12)+x(t)-x(M1)" actx="26.938823088123698" y="y(P12)+y(t)-y(M1)" acty="-1.1299440489076482" shape="circle" fixed="true">Point à &quot;x(P19)+x(P76)-x(M2)&quot;, &quot;y(P19)+y(P76)-y(M2)&quot; </Point>
<Point name="P20" n="22" color="5" type="thick" target="true" bold="true" x="x(P9)+x(t)-x(M1)" actx="27.592758206167105" y="y(P9)+y(t)-y(M1)" acty="-2.0510197862724797" shape="circle" fixed="true">Point à &quot;x(P16)+x(P76)-x(M2)&quot;, &quot;y(P16)+y(P76)-y(M2)&quot; </Point>
<Point name="P21" n="23" color="5" type="thick" target="true" bold="true" x="x(P10)+x(t)-x(M1)" actx="29.67065164933122" y="y(P10)+y(t)-y(M1)" acty="-1.780639634761087" shape="circle" fixed="true">Point à &quot;x(P17)+x(P76)-x(M2)&quot;, &quot;y(P17)+y(P76)-y(M2)&quot; </Point>
<Point name="P22" n="24" color="5" type="thick" target="true" bold="true" x="x(P11)+x(t)-x(M1)" actx="29.266497519943215" y="y(P11)+y(t)-y(M1)" acty="-1.21138352285675" shape="circle" fixed="true">Point à &quot;x(P18)+x(P76)-x(M2)&quot;, &quot;y(P18)+y(P76)-y(M2)&quot; </Point>
<Point name="P23" n="25" color="3" type="thick" target="true" bold="true" x="x(P17)+x(t)-x(M1)" actx="24.68672629120659" y="y(P17)+y(t)-y(M1)" acty="0.23756282481800595" shape="diamond" fixed="true">Point à &quot;x(P24)+x(P76)-x(M2)&quot;, &quot;y(P24)+y(P76)-y(M2)&quot; </Point>
<Point name="P24" n="26" color="3" type="thick" target="true" bold="true" x="x(P13)+x(t)-x(M1)" actx="27.41855485241411" y="y(P13)+y(t)-y(M1)" acty="-0.41313276103543295" shape="diamond" fixed="true">Point à &quot;x(P20)+x(P76)-x(M2)&quot;, &quot;y(P20)+y(P76)-y(M2)&quot; </Point>
<Point name="P25" n="27" color="3" type="thick" target="true" bold="true" x="x(P14)+x(t)-x(M1)" actx="30.78065706845419" y="y(P14)+y(t)-y(M1)" acty="0.02435151399334634" shape="diamond" fixed="true">Point à &quot;x(P21)+x(P76)-x(M2)&quot;, &quot;y(P21)+y(P76)-y(M2)&quot; </Point>
<Point name="P26" n="28" color="3" type="thick" target="true" bold="true" x="x(P15)+x(t)-x(M1)" actx="30.126721950410783" y="y(P15)+y(t)-y(M1)" acty="0.9454272513581778" shape="diamond" fixed="true">Point à &quot;x(P22)+x(P76)-x(M2)&quot;, &quot;y(P22)+y(P76)-y(M2)&quot; </Point>
<Point name="P27" n="29" color="3" type="thick" target="true" bold="true" x="x(P16)+x(t)-x(M1)" actx="26.3604656049827" y="y(P16)+y(t)-y(M1)" acty="1.0771990882337357" shape="diamond" fixed="true">Point à &quot;x(P23)+x(P76)-x(M2)&quot;, &quot;y(P23)+y(P76)-y(M2)&quot; </Point>
<Point name="P28" n="30" color="5" type="thick" target="true" bold="true" x="x(P8)+x(t)-x(M1)" actx="25.904395303903136" y="y(P8)+y(t)-y(M1)" acty="-1.6488677978855288" shape="circle" fixed="true">Point à &quot;x(P13)+x(P76)-x(M2)&quot;, &quot;y(P13)+y(P76)-y(M2)&quot; </Point>
<Point name="P29" n="31" color="1" target="true" bold="true" x="2*x(t)-x(P27)" actx="29.38878470200465" y="2*y(t)-y(P27)" acty="0.8695172418049117" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P34)&quot;, &quot;2*y(P76)-y(P34)&quot; </Point>
<Point name="P30" n="32" color="1" target="true" bold="true" x="2*x(t)-x(P23)" actx="31.06252401578076" y="2*y(t)-y(P23)" acty="1.7091535052206415" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P30)&quot;, &quot;2*y(P76)-y(P30)&quot; </Point>
<Point name="P31" n="33" color="1" target="true" bold="true" x="2*x(t)-x(P24)" actx="28.330695454573238" y="2*y(t)-y(P24)" acty="2.3598490910740804" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P31)&quot;, &quot;2*y(P76)-y(P31)&quot; </Point>
<Point name="P32" n="34" color="1" target="true" bold="true" x="2*x(t)-x(P25)" actx="24.96859323853316" y="2*y(t)-y(P25)" acty="1.922364816045301" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P32)&quot;, &quot;2*y(P76)-y(P32)&quot; </Point>
<Point name="P33" n="35" color="1" target="true" bold="true" x="2*x(t)-x(P26)" actx="25.622528356576566" y="2*y(t)-y(P26)" acty="1.0012890786804696" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P33)&quot;, &quot;2*y(P76)-y(P33)&quot; </Point>
<Point name="P34" n="36" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P19)" actx="28.81042721886365" y="2*y(t)-y(P19)" acty="3.0766603789462956" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P26)&quot;, &quot;2*y(P76)-y(P26)&quot; </Point>
<Point name="P35" n="37" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P28)" actx="29.844855003084213" y="2*y(t)-y(P28)" acty="3.5955841279241763" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P35)&quot;, &quot;2*y(P76)-y(P35)&quot; </Point>
<Point name="P36" n="38" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P21)" actx="26.07859865765613" y="2*y(t)-y(P21)" acty="3.7273559647997345" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P28)&quot;, &quot;2*y(P76)-y(P28)&quot; </Point>
<Point name="P37" n="39" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P22)" actx="26.482752787044134" y="2*y(t)-y(P22)" acty="3.1580998528953974" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P29)&quot;, &quot;2*y(P76)-y(P29)&quot; </Point>
<Point name="P38" n="40" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P20)" actx="28.156492100820245" y="2*y(t)-y(P20)" acty="3.997736116311127" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P27)&quot;, &quot;2*y(P76)-y(P27)&quot; </Point>
<Segment name="s1" n="41" color="1" target="true" ctag0="thin" cexpr0="((a(P28,P20,P21)&gt;180)&amp;&amp;(a(P21,P20,P24)&gt;180))" from="P20" to="P21">Segment de P20 à P21</Segment>
<Segment name="s2" n="42" color="1" target="true" ctag0="thin" cexpr0="((a(P20,P21,P22)&gt;180)&amp;&amp;(a(P22,P21,P25)&gt;180))" from="P21" to="P22">Segment de P21 à P22</Segment>
<Segment name="s3" n="43" color="1" target="true" ctag0="thin" cexpr0="((a(P21,P22,P19)&gt;180)&amp;&amp;(a(P19,P22,P26)&gt;180))" from="P22" to="P19">Segment de P22 à P19</Segment>
<Segment name="s4" n="44" color="1" target="true" ctag0="thin" cexpr0="((a(P22,P19,P28)&gt;180)&amp;&amp;(a(P28,P19,P27)&gt;180))" from="P19" to="P28">Segment de P19 à P28</Segment>
<Segment name="s5" n="45" color="1" target="true" ctag0="thin" cexpr0="((a(P19,P28,P20)&gt;180)&amp;&amp;(a(P20,P28,P23)&gt;180))" from="P28" to="P20">Segment de P28 à P20</Segment>
<Segment name="s6" n="46" color="1" target="true" ctag0="thin" cexpr0="((a(P38,P36,P32)&gt;180)&amp;&amp;(a(P32,P36,P37)&gt;180))" from="P36" to="P32">Segment de P36 à P32</Segment>
<Segment name="s7" n="47" color="1" target="true" ctag0="thin" cexpr0="((a(P36,P37,P33)&gt;180)&amp;&amp;(a(P33,P37,P34)&gt;180))" from="P37" to="P33">Segment de P37 à P33</Segment>
<Segment name="s8" n="48" color="1" target="true" ctag0="thin" cexpr0="((a(P37,P34,P29)&gt;180)&amp;&amp;(a(P29,P34,P35)&gt;180))" from="P34" to="P29">Segment de P34 à P29</Segment>
<Segment name="s9" n="49" color="1" target="true" ctag0="thin" cexpr0="((a(P35,P38,P31)&gt;180)&amp;&amp;(a(P31,P38,P36)&gt;180))" from="P38" to="P31">Segment de P38 à P31</Segment>
<Segment name="s10" n="50" color="1" target="true" ctag0="thin" cexpr0="((a(P36,P32,P27)&gt;180)&amp;&amp;(a(P27,P32,P23)&gt;180))" from="P32" to="P27">Segment de P32 à P27</Segment>
<Segment name="s11" n="51" color="1" target="true" ctag0="thin" cexpr0="((a(P27,P32,P23)&gt;180)&amp;&amp;(a(P23,P32,P36)&gt;180))" from="P32" to="P23">Segment de P32 à P23</Segment>
<Segment name="s12" n="52" color="1" target="true" ctag0="thin" cexpr0="((a(P37,P33,P23)&gt;180)&amp;&amp;(a(P23,P33,P24)&gt;180))" from="P33" to="P23">Segment de P33 à P23</Segment>
<Segment name="s13" n="53" color="1" target="true" ctag0="thin" cexpr0="((a(P34,P29,P24)&gt;180)&amp;&amp;(a(P24,P29,P25)&gt;180))" from="P29" to="P24">Segment de P29 à P24</Segment>
<Segment name="s14" n="54" color="1" target="true" ctag0="thin" cexpr0="((a(P24,P29,P25)&gt;180)&amp;&amp;(a(P25,P29,P34)&gt;180))" from="P29" to="P25">Segment de P29 à P25</Segment>
<Segment name="s15" n="55" color="1" target="true" ctag0="thin" cexpr0="((a(P35,P30,P25)&gt;180)&amp;&amp;(a(P25,P30,P26)&gt;180))" from="P30" to="P25">Segment de P30 à P25</Segment>
<Segment name="s16" n="56" color="1" target="true" ctag0="thin" cexpr0="((a(P25,P30,P26)&gt;180)&amp;&amp;(a(P26,P30,P35)&gt;180))" from="P30" to="P26">Segment de P30 à P26</Segment>
<Segment name="s17" n="57" color="1" target="true" ctag0="thin" cexpr0="((a(P38,P31,P26)&gt;180)&amp;&amp;(a(P26,P31,P27)&gt;180))" from="P31" to="P26">Segment de P31 à P26</Segment>
<Segment name="s18" n="58" color="1" target="true" ctag0="thin" cexpr0="((a(P26,P31,P27)&gt;180)&amp;&amp;(a(P27,P31,P38)&gt;180))" from="P31" to="P27">Segment de P31 à P27</Segment>
<Segment name="s19" n="59" color="1" target="true" ctag0="thin" cexpr0="((a(P26,P30,P35)&gt;180)&amp;&amp;(a(P35,P30,P25)&gt;180))" from="P30" to="P35">Segment de P30 à P35</Segment>
<Segment name="s20" n="60" color="1" target="true" ctag0="thin" cexpr0="((a(P36,P38,P35)&gt;180)&amp;&amp;(a(P35,P38,P31)&gt;180))" from="P38" to="P35">Segment de P38 à P35</Segment>
<Segment name="s21" n="61" color="1" target="true" ctag0="thin" cexpr0="((a(P38,P35,P34)&gt;180)&amp;&amp;(a(P34,P35,P30)&gt;180))" from="P35" to="P34">Segment de P35 à P34</Segment>
<Segment name="s22" n="62" color="1" target="true" ctag0="thin" cexpr0="((a(P35,P34,P37)&gt;180)&amp;&amp;(a(P37,P34,P29)&gt;180))" from="P34" to="P37">Segment de P34 à P37</Segment>
<Segment name="s23" n="63" color="1" target="true" ctag0="thin" cexpr0="((a(P34,P37,P36)&gt;180)&amp;&amp;(a(P36,P37,P33)&gt;180))" from="P37" to="P36">Segment de P37 à P36</Segment>
<Segment name="s24" n="64" color="1" target="true" ctag0="thin" cexpr0="((a(P37,P36,P38)&gt;180)&amp;&amp;(a(P38,P36,P32)&gt;180))" from="P36" to="P38">Segment de P36 à P38</Segment>
<Segment name="s25" n="65" color="1" target="true" ctag0="thin" cexpr0="((a(P33,P24,P20)&gt;180)&amp;&amp;(a(P20,P24,P29)&gt;180))" from="P24" to="P20">Segment de P24 à P20</Segment>
<Segment name="s26" n="66" color="1" target="true" ctag0="thin" cexpr0="((a(P29,P25,P21)&gt;180)&amp;&amp;(a(P21,P25,P30)&gt;180))" from="P25" to="P21">Segment de P25 à P21</Segment>
<Segment name="s27" n="67" color="1" target="true" ctag0="thin" cexpr0="((a(P30,P26,P22)&gt;180)&amp;&amp;(a(P22,P26,P31)&gt;180))" from="P26" to="P22">Segment de P26 à P22</Segment>
<Segment name="s28" n="68" color="1" target="true" ctag0="thin" cexpr0="((a(P31,P27,P19)&gt;180)&amp;&amp;(a(P19,P27,P32)&gt;180))" from="P27" to="P19">Segment de P27 à P19</Segment>
<Segment name="s29" n="69" color="1" target="true" ctag0="thin" cexpr0="((a(P32,P23,P28)&gt;180)&amp;&amp;(a(P28,P23,P33)&gt;180))" from="P23" to="P28">Segment de P23 à P28</Segment>
<Segment name="s30" n="70" color="1" target="true" ctag0="thin" cexpr0="((a(P23,P33,P24)&gt;180)&amp;&amp;(a(P24,P33,P37)&gt;180))" from="P33" to="P24">Segment de P33 à P24</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dtetra" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="G">G</Parameter>
<Objects>
<Point name="G" n="0" color="2" showname="true" xcoffset="0.2733753263073275" ycoffset="0.08411548501764265" keepclose="true" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(G)-windowcx)+windowcx+d(windowcx)" actx="3.243678253571642" y="(windoww/(windoww-d(windoww)))*(y(G)-windowcy)+windowcy+d(windowcy)" acty="0.7617185353379072" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(G)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(G)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Point name="O" n="1" mainparameter="true" x="-1.656048748705789" y="0.6145164365570406">Point à -1.65605, 0.61452</Point>
<Expression name="E1" n="2" color="1" showname="true" showvalue="true" x="x(G)" y="y(G)+15/pixel" value="2" prompt="k" fixed="true">Expression &quot;2&quot; à 3.24368, 1.07715</Expression>
<Point name="X" n="3" mainparameter="true" x="-2.2623898699385574" y="0.38163166366691936">Point à -2.26239, 0.38163</Point>
<Point name="Y" n="4" mainparameter="true" x="-0.8608440928453058" y="0.4369425085479858">Point à -0.86084, 0.43694</Point>
<Point name="Z" n="5" mainparameter="true" x="-1.656048748705789" y="1.5706713312510705">Point à -1.65605, 1.57067</Point>
<Point name="P6" n="6" color="2" showname="true" xcoffset="0.04205774250882044" ycoffset="0.25589304175426086" keepclose="true" target="true" bold="true" x="x(G)+(sqrt(3)/sqrt(2))*E1*(x(Z)-x(O))" actx="3.243678253571642" y="y(G)+(sqrt(3)/sqrt(2))*E1*(y(Z)-y(O))" acty="3.103810142402863" shape="circle" fixed="true">Point à &quot;x(C)+x(B)-x(A)&quot;, &quot;y(C)+y(B)-y(A)&quot; </Point>
<Point name="P7" n="7" color="2" hidden="super" showname="true" x="x(G)" actx="3.243678253571642" y="y(G)-(y(P6)-y(G))/3" acty="-0.018978667017077955" shape="circle" fixed="true">Point à &quot;x(G)&quot;, &quot;y(G)-(y(P6)-y(G))/3&quot;</Point>
<Point name="P8" n="8" color="2" hidden="super" showname="true" x="x(P7)+E1*(x(X)-x(O))/sqrt(3)" actx="2.5435358344426855" y="y(P7)+E1*(y(X)-y(O))/sqrt(3)" acty="-0.28789083965363066" shape="circle" fixed="true">Point à &quot;x(P7)+E1*(x(X)-x(O))/sqrt(3)&quot;, &quot;y(P7)+E1*(y(X)-y(O))/sqrt(3)&quot;</Point>
<Point name="P9" n="9" color="2" target="true" x="x(P7)+2*(x(P7)-x(P8))" actx="4.643963091829555" y="y(P7)+2*(y(P7)-y(P8))" acty="0.5188456782560275" shape="circle" fixed="true">Point à &quot;x(P7)+2*(x(P7)-x(P8))&quot;, &quot;y(P7)+2*(y(P7)-y(P8))&quot;</Point>
<Point name="P10" n="10" color="2" target="true" bold="true" x="x(P8)+E1*(x(Y)-x(O))" actx="4.1339451461636525" y="y(P8)+E1*(y(Y)-y(O))" acty="-0.6430386956717402" shape="circle" fixed="true">Point à &quot;x(C)+x(B)-x(A)&quot;, &quot;y(C)+y(B)-y(A)&quot; </Point>
<Point name="P11" n="11" color="2" target="true" bold="true" x="x(P8)+E1*(x(O)-x(Y))" actx="0.9531265227217189" y="y(P8)+E1*(y(O)-y(Y))" acty="0.06725701636447895" shape="circle" fixed="true">Point à &quot;x(C)+x(B)-x(A)&quot;, &quot;y(C)+y(B)-y(A)&quot; </Point>
<Segment name="s1" n="12" color="2" target="true" ctag0="thin" cexpr0="((a(P9,P6,P11)&gt;180)&amp;&amp;(a(P11,P6,P10)&gt;180))" from="P6" to="P11">Segment de P6 à P11</Segment>
<Segment name="s2" n="13" color="2" target="true" ctag0="thin" cexpr0="((a(P11,P6,P10)&gt;180)&amp;&amp;(a(P10,P6,P9)&gt;180))" from="P6" to="P10">Segment de P6 à P10</Segment>
<Segment name="s3" n="14" color="2" target="true" ctag0="thin" cexpr0="((a(P10,P6,P9)&gt;180)&amp;&amp;(a(P9,P6,P11)&gt;180))" from="P6" to="P9">Segment de P6 à P9</Segment>
<Segment name="s4" n="15" color="2" xcoffset="0.857441230031186" ycoffset="0.09819214404524133" keepclose="true" target="true" ctag0="thin" cexpr0="((a(P10,P9,P11)&gt;180)&amp;&amp;(a(P11,P9,P6)&gt;180))" from="P9" to="P11">Segment de P9 à P11</Segment>
<Segment name="s5" n="16" color="2" xcoffset="0.7535763009733026" ycoffset="0.8705187569218046" keepclose="true" target="true" ctag0="thin" cexpr0="((a(P11,P10,P9)&gt;180)&amp;&amp;(a(P9,P10,P6)&gt;180))" from="P10" to="P9">Segment de P10 à P9</Segment>
<Segment name="s6" n="17" color="2" target="true" ctag0="thin" cexpr0="((a(P9,P11,P10)&gt;180)&amp;&amp;(a(P10,P11,P6)&gt;180))" from="P11" to="P10">Segment de P11 à P10</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Darete" showduplicates="true">
<Parameter name="D">D</Parameter>
<Parameter name="E">E</Parameter>
<Parameter name="F">F</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="B" n="0" mainparameter="true" x="57.39830561238021" y="21.248404810689276">Point à 57.39831, 21.2484</Point>
<Point name="E" n="1" mainparameter="true" x="57.39830561238021" y="25.150349928536837">Point à 57.39831, 25.15035</Point>
<Point name="D" n="2" mainparameter="true" x="55.04321691709174" y="25.861847914336575">Point à 55.04322, 25.86185</Point>
<Point name="F" n="3" mainparameter="true" x="60.63150174582529" y="25.668611362089926">Point à 60.6315, 25.66861</Point>
<Segment name="s1" n="4" color="1" target="true" ctag0="thin" cexpr0="((a(D,E,B)&gt;180)&amp;&amp;(a(B,E,F)&gt;180))" from="E" to="B">Segment de E à B</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dcube" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="o">o</Parameter>
<Objects>
<Point name="o" n="0" type="thick" showname="true" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(o)-windowcx)+windowcx+d(windowcx)" actx="82.56393431232262" y="(windoww/(windoww-d(windoww)))*(y(o)-windowcy)+windowcy+d(windowcy)" acty="23.724926967364365" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(o)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(o)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Point name="O" n="1" mainparameter="true" x="74.67689368762788" y="21.581489236136537">Point à 74.67689, 21.58149</Point>
<Expression name="E1" n="2" color="2" showname="true" showvalue="true" x="x(o)" y="y(o)+15/pixel" value="2" prompt="k" fixed="true">Expression &quot;2&quot; à 82.56393, 23.87493</Expression>
<Point name="X" n="3" mainparameter="true" x="74.93019145339206" y="21.394237681230273">Point à 74.93019, 21.39424</Point>
<Point name="Y" n="4" mainparameter="true" x="75.6442820490727" y="21.630518563327104">Point à 75.64428, 21.63052</Point>
<Point name="Z" n="5" mainparameter="true" x="74.67689368762788" y="22.562576887807086">Point à 74.67689, 22.56258</Point>
<Point name="P6" n="6" showname="true" target="true" x="x(o)+E1*(-x(X)+x(Y)-x(Z)+x(O))" actx="83.9921155036839" y="y(o)+E1*(-y(X)+y(Y)-y(Z)+y(O))" acty="22.23531342821693" shape="circle" fixed="true">Point à &quot;x(o)+E1*(-x(X)+x(Y)-x(Z)+x(O))&quot;, &quot;y(o)+E1*(-y(X)+y(Y)-y(Z)+y(O))&quot;</Point>
<Point name="P7" n="7" showname="true" target="true" x="x(o)+E1*(-x(X)+x(Y)+x(Z)-x(O))" actx="83.9921155036839" y="y(o)+E1*(-y(X)+y(Y)+y(Z)-y(O))" acty="26.159664034899127" shape="circle" fixed="true">Point à &quot;x(o)+E1*(-x(X)+x(Y)+x(Z)-x(O))&quot;, &quot;y(o)+E1*(-y(X)+y(Y)+y(Z)-y(O))&quot;</Point>
<Point name="P8" n="8" target="true" x="x(o)+E1*(x(X)+x(Y)-x(Z)-x(O))" actx="85.00530656674057" y="y(o)+E1*(y(X)+y(Y)-y(Z)-y(O))" acty="21.486307208591878" shape="circle" fixed="true">Point à &quot;x(o)+E1*(x(X)+x(Y)-x(Z)-x(O))&quot;, &quot;y(o)+E1*(y(X)+y(Y)-y(Z)-y(O))&quot;</Point>
<Point name="P9" n="9" showname="true" target="true" x="x(o)+E1*(x(X)+x(Y)+x(Z)-3*x(O))" actx="85.00530656674057" y="y(o)+E1*(y(X)+y(Y)+y(Z)-3*y(O))" acty="25.41065781527406" shape="circle" fixed="true">Point à &quot;x(o)+E1*(x(X)+x(Y)+x(Z)-3*x(O))&quot;, &quot;y(o)+E1*(y(X)+y(Y)+y(Z)-3*y(O))&quot;</Point>
<Point name="P10" n="10" showname="true" target="true" x="2*x(o)-x(P9)" actx="80.12256205790466" y="2*y(o)-y(P9)" acty="22.03919611945467" shape="circle" fixed="true">Point à &quot;2*x(o)-x(P9)&quot;, &quot;2*y(o)-y(P9)&quot;</Point>
<Point name="P11" n="11" showname="true" target="true" x="2*x(o)-x(P8)" actx="80.12256205790466" y="2*y(o)-y(P8)" acty="25.96354672613685" shape="circle" fixed="true">Point à &quot;2*x(o)-x(P8)&quot;, &quot;2*y(o)-y(P8)&quot;</Point>
<Point name="P12" n="12" target="true" x="2*x(o)-x(P7)" actx="81.13575312096133" y="2*y(o)-y(P7)" acty="21.290189899829603" shape="circle" fixed="true">Point à &quot;2*x(o)-x(P7)&quot;, &quot;2*y(o)-y(P7)&quot;</Point>
<Point name="P13" n="13" target="true" x="2*x(o)-x(P6)" actx="81.13575312096133" y="2*y(o)-y(P6)" acty="25.2145405065118" shape="circle" fixed="true">Point à &quot;2*x(o)-x(P6)&quot;, &quot;2*y(o)-y(P6)&quot;</Point>
<Segment name="s1" n="14" color="1" target="true" ctag0="thin" cexpr0="((a(P9,P7,P6)&gt;180)&amp;&amp;(a(P6,P7,P11)&gt;180))" from="P7" to="P6">Segment de P7 à P6</Segment>
<Segment name="s2" n="15" color="1" target="true" ctag0="thin" cexpr0="((a(P7,P11,P10)&gt;180)&amp;&amp;(a(P10,P11,P13)&gt;180))" from="P11" to="P10">Segment de P11 à P10</Segment>
<Segment name="s3" n="16" color="1" target="true" ctag0="thin" cexpr0="((a(P11,P13,P12)&gt;180)&amp;&amp;(a(P12,P13,P9)&gt;180))" from="P13" to="P12">Segment de P13 à P12</Segment>
<Segment name="s4" n="17" color="1" target="true" ctag0="thin" cexpr0="((a(P13,P9,P8)&gt;180)&amp;&amp;(a(P8,P9,P7)&gt;180))" from="P9" to="P8">Segment de P9 à P8</Segment>
<Segment name="s5" n="18" color="1" target="true" ctag0="thin" cexpr0="((a(P10,P11,P13)&gt;180)&amp;&amp;(a(P13,P11,P7)&gt;180))" from="P11" to="P13">Segment de P11 à P13</Segment>
<Segment name="s6" n="19" color="1" target="true" ctag0="thin" cexpr0="((a(P11,P7,P9)&gt;180)&amp;&amp;(a(P9,P7,P6)&gt;180))" from="P7" to="P9">Segment de P7 à P9</Segment>
<Segment name="s7" n="20" color="1" target="true" ctag0="thin" cexpr0="((a(P12,P13,P9)&gt;180)&amp;&amp;(a(P9,P13,P11)&gt;180))" from="P13" to="P9">Segment de P13 à P9</Segment>
<Segment name="s8" n="21" color="1" target="true" ctag0="thin" cexpr0="((a(P13,P11,P7)&gt;180)&amp;&amp;(a(P7,P11,P10)&gt;180))" from="P11" to="P7">Segment de P11 à P7</Segment>
<Segment name="s9" n="22" color="1" target="true" ctag0="thin" cexpr0="((a(P9,P8,P12)&gt;180)&amp;&amp;(a(P12,P8,P6)&gt;180))" from="P8" to="P12">Segment de P8 à P12</Segment>
<Segment name="s10" n="23" color="1" target="true" ctag0="thin" cexpr0="((a(P7,P6,P8)&gt;180)&amp;&amp;(a(P8,P6,P10)&gt;180))" from="P6" to="P8">Segment de P6 à P8</Segment>
<Segment name="s11" n="24" color="1" target="true" ctag0="thin" cexpr0="((a(P11,P10,P6)&gt;180)&amp;&amp;(a(P6,P10,P12)&gt;180))" from="P10" to="P6">Segment de P10 à P6</Segment>
<Segment name="s12" n="25" color="1" target="true" ctag0="thin" cexpr0="((a(P13,P12,P10)&gt;180)&amp;&amp;(a(P10,P12,P8)&gt;180))" from="P12" to="P10">Segment de P12 à P10</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dcoords" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="p">p</Parameter>
<Objects>
<Point name="O" n="0" mainparameter="true" x="1.039330361771076" y="0.4647413122295063">Point à 1.03933, 0.46474</Point>
<Point name="p" n="1" color="1" mainparameter="true" x="(windoww/(windoww-d(windoww)))*(x(p)-windowcx)+windowcx+d(windowcx)" actx="-2.5312001282524608" y="(windoww/(windoww-d(windoww)))*(y(p)-windowcy)+windowcy+d(windowcy)" acty="1.4860679450064422" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(p)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(p)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Expression name="E1" n="2" color="1" type="thick" showname="true" showvalue="true" x="x(p)+7/pixel" y="y(p)+25/pixel" value="1" prompt="x" fixed="true">Expression &quot;0&quot; à -6.68004, 2.71076 </Expression>
<Expression name="E2" n="3" color="1" type="thick" showname="true" showvalue="true" x="x(p)+7/pixel" y="y(p)-4/pixel" value="2" prompt="y" fixed="true">Expression &quot;0&quot; à -5.68004, 2.71076 </Expression>
<Expression name="E3" n="4" color="1" type="thick" showname="true" showvalue="true" x="x(p)+7/pixel" y="y(p)-30/pixel" value="-1" prompt="z" fixed="true">Expression &quot;0&quot; à -4.58004, 2.71076 </Expression>
<Point name="P3" n="5" color="1" hidden="super" x="x(p)" actx="-2.5312001282524608" y="y(p)+40/pixel" acty="1.8860679450064421" fixed="true">Point à &quot;x(p)&quot;, &quot;y(p)+40/pixel&quot;</Point>
<Point name="P4" n="6" color="1" hidden="super" x="x(p)" actx="-2.5312001282524608" y="y(p)-35/pixel" acty="1.1360679450064421" fixed="true">Point à &quot;x(p)&quot;, &quot;y(p)-35/pixel&quot;</Point>
<Segment name="s1" n="7" color="1" target="true" from="P3" to="P4">Segment de P3 à P4</Segment>
<Point name="Z" n="8" mainparameter="true" x="1.015777063361877" y="1.4596355468668833">Point à 1.01578, 1.45964</Point>
<Point name="Y" n="9" mainparameter="true" x="2.0062468607753905" y="0.4624709308305989">Point à 2.00625, 0.46247</Point>
<Point name="X" n="10" mainparameter="true" x="0.7853269639841833" y="0.363843803570412">Point à 0.78533, 0.36384</Point>
<Point name="P8" n="11" color="1" target="true" bold="true" large="true" x="x(O)+E1*(x(X)-x(O))+E2*(x(Y)-x(O))+E3*(x(Z)-x(O))" actx="2.7427132604020112" y="y(O)+E1*(y(X)-y(O))+E2*(y(Y)-y(O))+E3*(y(Z)-y(O))" acty="-0.6355911938647798" fixed="true">Point à &quot;x(O)+E1*(x(X)-x(O))+E2*(x(Y)-x(O))+E3*(x(Z)-x(O))&quot;, &quot;y(O)+E1*(y(X)-y(O))+E2*(y(Y)-y(O))+E3*(y(Z)-y(O))&quot; </Point>
</Objects>
<PromptFor object0="E1" prompt0="x" object1="E2" prompt1="y" object2="E3" prompt2="z"/>
</Macro>
<Macro Name="@builtin@/symc" showduplicates="true">
<Parameter name="O">O</Parameter>
<Parameter name="M">M</Parameter>
<Objects>
<Point name="M" n="0" parameter="true" mainparameter="true" x="-2.569343065693431" y="4.131386861313868">Point à -2.57, 4.13</Point>
<Point name="O" n="1" parameter="true" mainparameter="true" x="-0.6715328467153284" y="2.700729927007301">Point à -0.67, 2.7</Point>
<Point name="P3" n="2" showname="true" target="true" x="2*x(O)-x(M)" actx="1.2262773722627742" y="2*y(O)-y(M)" acty="1.270072992700734" shape="circle" fixed="true">Point à &quot;2*x(O)-x(M)&quot;, &quot;2*y(O)-y(M)&quot;</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/syma" showduplicates="true">
<Parameter name="l1">l1</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Point name="A" n="0" x="1.2070175438596493" y="2.091228070175438">Point à 1.20702, 2.09123</Point>
<Point name="P6" n="1" color="2" hidden="super" x="x(A)" actx="1.2070175438596493" y="y(A)" acty="2.091228070175438" shape="circle" fixed="true">Point à &quot;x(A)&quot;, &quot;y(A)&quot;</Point>
<Line name="l1" n="2">???</Line>
<PointOn name="P1" n="3" color="2" hidden="super" on="l1" alpha="9.884663042646986" x="2.34672934754846" y="-0.6763300486598602" shape="circle">Point sur l1</PointOn>
<Plumb name="perp1" n="4" color="3" hidden="super" point="P6" line="l1" valid="true">Perpendiculaire passant par P6 à l1</Plumb>
<Circle name="c1" n="5" color="4" hidden="super" through="P6" midpoint="P1" acute="true">Cercle de centre P1 passant par P6</Circle>
<Intersection name="P2" n="6" color="2" target="true" first="perp1" second="c1" awayfrom="P6" shape="circle" which="second">Intersection entre perp1 et c1</Intersection>
</Objects>
</Macro>
<Macro Name="@builtin@/trans" showduplicates="true">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="M">M</Parameter>
<Objects>
<Point name="A" n="0" parameter="true" mainparameter="true" x="-3.9850187265917603" y="1.730337078651686">Point à -3.98502, 1.73034 </Point>
<Point name="B" n="1" parameter="true" mainparameter="true" x="-2.411985018726593" y="2.269662921348316">Point à -2.41199, 2.26966 </Point>
<Point name="M" n="2" parameter="true" mainparameter="true" x="-1.8726591760299642" y="1.1610486891385765">Point à -1.87266, 1.16105 </Point>
<Point name="P4" n="3" target="true" bold="true" x="x(M)+x(B)-x(A)" actx="-0.2996254681647974" y="y(M)+y(B)-y(A)" acty="1.7003745318352066" fixed="true">Point à &quot;x(C)+x(B)-x(A)&quot;, &quot;y(C)+y(B)-y(A)&quot; </Point>
</Objects>
</Macro>
<Macro Name="@builtin@/med" showduplicates="true">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="B" n="0" parameter="true" mainparameter="true" x="3.9216109938781707" y="-13.620312471643809">Point à 3.92161, -13.62031 </Point>
<Point name="A" n="1" parameter="true" mainparameter="true" x="-4.980460890948463" y="17.297848688565512">Point à -4.98046, 17.29785 </Point>
<Circle name="c1" n="2" color="2" hidden="super" bold="true" through="B" midpoint="A" acute="true">Cercle de centre P81 passant par I64</Circle>
<Circle name="c2" n="3" color="2" hidden="super" bold="true" through="A" midpoint="B" acute="true">Cercle de centre I64 passant par P81</Circle>
<Intersection name="I1" n="4" color="2" hidden="super" bold="true" first="c2" second="c1" shape="circle" which="second">Intersection entre c2 et c1</Intersection>
<Intersection name="I2" n="5" color="2" hidden="super" bold="true" first="c1" second="c2" shape="circle" which="second">Intersection entre c1 et c2</Intersection>
<Line name="l1" n="6" color="2" target="true" bold="true" from="I1" to="I2">Droite passant par I1 et I2</Line>
</Objects>
</Macro>
<Macro Name="@builtin@/biss" showduplicates="true">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Objects>
<Point name="A" n="0" mainparameter="true" x="-0.3037974683544302" y="2.2582278481012654">Point à -0.3038, 2.25823</Point>
<Point name="B" n="1" mainparameter="true" x="-2.470886075949367" y="0.6177215189873415">Point à -2.47089, 0.61772</Point>
<Point name="C" n="2" mainparameter="true" x="3.281012658227848" y="0.2126582278481015">Point à 3.28101, 0.21266</Point>
<Angle name="a1" n="3" color="1" hidden="super" showvalue="true" unit="°" first="C" root="B" filled="true" fixed="angle180(a(C,B,A))/2">Angle C - B de mesure angle180(a(C,B,A))/2</Angle>
<Circle name="c1" n="4" color="4" hidden="super" through="C" midpoint="B" acute="true">Cercle de centre B passant par C</Circle>
<Intersection name="E" n="5" hidden="super" showname="true" first="a1" second="c1" shape="circle" which="first">Intersection entre a1 et c1</Intersection>
<Ray name="r1" n="6" color="3" target="true" from="B" to="E">Demi-droite d&apos;origine B vers E</Ray>
</Objects>
</Macro>
<Macro Name="@builtin@/circ" showduplicates="true">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Objects>
<Point name="A" n="0" parameter="true" mainparameter="true" x="-5.896980461811722" y="0.7246891651865007">Point à -5.89698, 0.72469 </Point>
<Point name="B" n="1" parameter="true" mainparameter="true" x="-3.7087033747779783" y="2.5435168738898746">Point à -3.7087, 2.54352 </Point>
<Point name="C" n="2" parameter="true" mainparameter="true" x="-0.12788632326820618" y="-0.4973357015985789">Point à -0.12789, -0.49734 </Point>
<Point name="P4" n="3" color="3" target="true" bold="true" x="(x(A)^2*y(C)-x(A)^2*y(B)+y(C)^2*y(B)-y(C)^2*y(A)-y(C)*y(B)^2+y(C)*y(A)^2-y(C)*x(B)^2+y(B)^2*y(A)-y(B)*y(A)^2+y(B)*x(C)^2+y(A)*x(B)^2-y(A)*x(C)^2)/(2*x(A)*y(C)+(-(2*x(A)))*y(B)+(-(2*y(C)))*x(B)+2*y(B)*x(C)+2*y(A)*x(B)+(-(2*y(A)))*x(C))" actx="-3.1193971099479443" y="(-x(A)^2*x(C)+x(A)^2*x(B)+x(A)*x(C)^2-x(A)*x(B)^2+x(A)*y(C)^2-x(A)*y(B)^2-x(C)^2*x(B)+x(C)*x(B)^2+x(C)*y(B)^2-x(C)*y(A)^2-x(B)*y(C)^2+x(B)*y(A)^2)/(2*x(A)*y(C)+(-(2*x(A)))*y(B)+2*x(C)*y(B)+(-(2*x(C)))*y(A)+(-(2*x(B)))*y(C)+2*x(B)*y(A))" acty="-0.3912915155041797" shape="circle" fixed="true">Point à &quot;(x(A)^2*y(P4)-x(A)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(A)-y(P4)*y(P5)^2+y(P4)*y(A)^2-y(P4)*x(P5)^2+y(P5)^2*y(A)-y(P5)*y(A)^2+y(P5)*x(P4)^2+y(A)*x(P5)^2-y(A)*x(P4)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(A)*x(P5)+(-(2*y(A)))*x(P4))&quot;, &quot;(-x(A)^2*x(P4)+x(A)^2*x(P5)+x(A)*x(P4)^2-x(A)*x(P5)^2+x(A)*y(P4)^2-x(A)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(A)^2-x(P5)*y(P4)^2+x(P5)*y(A)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(A)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(A))&quot; </Point>
<Circle name="c1" n="4" color="3" target="true" large="true" through="A" midpoint="P4">Cercle de centre P10 passant par P7 </Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/arc" showduplicates="true">
<Parameter name="M">M</Parameter>
<Parameter name="N">N</Parameter>
<Parameter name="P">P</Parameter>
<Objects>
<Point name="M" n="0" parameter="true" mainparameter="true" x="-6.74" y="2.9000000000000012">Point à -6.74, 2.9 </Point>
<Point name="N" n="1" parameter="true" mainparameter="true" x="-5.507754235296662" y="4.133357046501844">Point à -5.50775, 4.13336 </Point>
<Point name="P" n="2" parameter="true" mainparameter="true" x="-4.560000000000001" y="3.86">Point à -4.56, 3.86 </Point>
<Point name="P4" n="3" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P,M,N)&lt;180,x(M),x(P))" actx="-6.74" y="if(a(P,M,N)&lt;180,y(M),y(P))" acty="2.9000000000000012" shape="circle" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(A),x(C))&quot;, &quot;if(a(C,A,B)&lt;180,y(A),y(C))&quot; </Point>
<Point name="P5" n="4" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P,M,N)&lt;180,x(P),x(M))" actx="-4.560000000000001" y="if(a(P,M,N)&lt;180,y(P),y(M))" acty="3.86" shape="diamond" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(C),x(A))&quot;, &quot;if(a(C,A,B)&lt;180,y(C),y(A))&quot; </Point>
<Point name="P6" n="5" hidden="super" x="(x(N)^2*y(P4)-x(N)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(N)-y(P4)*y(P5)^2+y(P4)*y(N)^2-y(P4)*x(P5)^2+y(P5)^2*y(N)-y(P5)*y(N)^2+y(P5)*x(P4)^2+y(N)*x(P5)^2-y(N)*x(P4)^2)/(2*x(N)*y(P4)+(-(2*x(N)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(N)*x(P5)+(-(2*y(N)))*x(P4))" actx="-5.385187132991038" y="(-x(N)^2*x(P4)+x(N)^2*x(P5)+x(N)*x(P4)^2-x(N)*x(P5)^2+x(N)*y(P4)^2-x(N)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(N)^2-x(P5)*y(P4)^2+x(P5)*y(N)^2)/(2*x(N)*y(P4)+(-(2*x(N)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(N)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(N))" acty="2.778654114500473" fixed="true">Point à &quot;(x(B)^2*y(P4)-x(B)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(B)-y(P4)*y(P5)^2+y(P4)*y(B)^2-y(P4)*x(P5)^2+y(P5)^2*y(B)-y(P5)*y(B)^2+y(P5)*x(P4)^2+y(B)*x(P5)^2-y(B)*x(P4)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(B)*x(P5)+(-(2*y(B)))*x(P4))&quot;, &quot;(-x(B)^2*x(P4)+x(B)^2*x(P5)+x(B)*x(P4)^2-x(B)*x(P5)^2+x(B)*y(P4)^2-x(B)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(B)^2-x(P5)*y(P4)^2+x(P5)*y(B)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(B)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(B))&quot; </Point>
<Circle name="c1" n="6" target="true" through="P4" midpoint="P6" start="P5" end="P4">Cercle de centre P6 passant par P4</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/t_align" showduplicates="true">
<Parameter name="M">M</Parameter>
<Parameter name="a">a</Parameter>
<Parameter name="b">b</Parameter>
<Parameter name="P4">P4</Parameter>
<Objects>
<Point name="a" n="0" mainparameter="true" x="-1.0" y="2.0">Point à -1.0, 2.0</Point>
<Point name="b" n="1" mainparameter="true" x="2.0" y="2.0">Point à 2.0, 2.0</Point>
<Line name="l1" n="2" color="2" hidden="super" from="a" to="b">Droite passant par a et b</Line>
<Point name="M" n="3" mainparameter="true" x="4.0" y="2.0">Point à 4.0, 2.0</Point>
<Plumb name="perp1" n="4" color="2" hidden="super" point="M" line="l1" valid="true">Perpendiculaire passant par M à l1</Plumb>
<Intersection name="I1" n="5" color="2" hidden="super" showname="true" first="perp1" second="l1" shape="circle">Intersection entre perp1 et l1</Intersection>
<Point name="P4" n="6" color="2" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P4)-windowcx)+windowcx+d(windowcx)" actx="-3.9869281045751634" y="(windoww/(windoww-d(windoww)))*(y(P4)-windowcy)+windowcy+d(windowcy)" acty="-2.0784313725490193" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P4)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P4)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="7" color="2" target="true" ctag0="green" cexpr0="1" x="if(d(M,I1)~=0,x(P4)+15/pixel,invalid)" y="y(P4)+13/pixel" fixed="true">Les points sont alignés</Text>
<Text name="Text2" n="8" color="2" target="true" ctag0="red" cexpr0="1" x="if(!(d(M,I1)~=0),x(P4)+15/pixel,invalid)" y="y(P4)+13/pixel" fixed="true">Les points ne sont pas alignés</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_para" showduplicates="true">
<Parameter name="l1">l1</Parameter>
<Parameter name="l2">l2</Parameter>
<Parameter name="P5">P5</Parameter>
<Objects>
<Line name="l1" n="0" mainparameter="true">???</Line>
<Line name="l2" n="1" mainparameter="true">???</Line>
<Point name="P5" n="2" color="1" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)" actx="-1.5" y="(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)" acty="-2.5" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="3" showname="true" showvalue="true" target="true" ctag0="red" cexpr0="1" x="if(x(l1)*y(l2)~=y(l1)*x(l2),invalid,x(P5)+15/pixel)" y="y(P5)+13/pixel" fixed="true">Les objets ne sont pas parallèles</Text>
<Text name="Text2" n="4" showname="true" showvalue="true" target="true" ctag0="green" cexpr0="1" x="if(x(l1)*y(l2)~=y(l1)*x(l2),x(P5)+15/pixel,invalid)" y="y(P5)+13/pixel" fixed="true">Les objets sont parallèles</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_perp" showduplicates="true">
<Parameter name="l1">l1</Parameter>
<Parameter name="l2">l2</Parameter>
<Parameter name="P5">P5</Parameter>
<Objects>
<Line name="l1" n="0">???</Line>
<Line name="l2" n="1">???</Line>
<Point name="P5" n="2" color="1" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)" actx="-6.0" y="(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)" acty="4.0" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="3" showname="true" showvalue="true" target="true" ctag0="red" cexpr0="1" x="if(x(l2)*x(l1)~=-y(l2)*y(l1),invalid,x(P5)+15/pixel)" y="y(P5)+13/pixel" fixed="true">Les objets ne sont pas perpendiculaires</Text>
<Text name="Text2" n="4" showname="true" showvalue="true" target="true" ctag0="green" cexpr0="1" x="if(x(l2)*x(l1)~=-y(l2)*y(l1),x(P5)+15/pixel,invalid)" y="y(P5)+13/pixel" fixed="true">Les objets sont perpendiculaires</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_equi" showduplicates="true">
<Parameter name="o">o</Parameter>
<Parameter name="a">a</Parameter>
<Parameter name="b">b</Parameter>
<Parameter name="P5">P5</Parameter>
<Objects>
<Point name="o" n="0" mainparameter="true" x="1.0" y="-1.0">Point à 1.0, -1.0</Point>
<Point name="a" n="1" mainparameter="true" x="1.0" y="3.0">Point à 1.0, 3.0</Point>
<Point name="b" n="2" mainparameter="true" x="5.0" y="0.0">Point à 5.0, 0.0</Point>
<Point name="P5" n="3" color="1" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)" actx="-4.321167883211679" y="(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)" acty="-3.635036496350365" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="4" target="true" ctag0="red" cexpr0="1" x="if(d(a,o)~=d(b,o),invalid,x(P5)+15/pixel)" y="y(P5)+13/pixel" fixed="true">Les points ne sont pas équidistants</Text>
<Text name="Text2" n="5" target="true" ctag0="green" cexpr0="1" x="if(d(a,o)~=d(b,o),x(P5)+15/pixel,invalid)" y="y(P5)+13/pixel" fixed="true">Les points sont équidistants</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_app" showduplicates="true">
<Parameter name="P13">P13</Parameter>
<Parameter name="l4">l4</Parameter>
<Parameter name="P14">P14</Parameter>
<Objects>
<Line name="l4" n="0" mainparameter="true">???</Line>
<Point name="P13" n="1" mainparameter="true" x="5.0" y="0.0">Point à 5.0, 0.0</Point>
<Point name="P14" n="2" color="2" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P14)-windowcx)+windowcx+d(windowcx)" actx="3.0065359477124183" y="(windoww/(windoww-d(windoww)))*(y(P14)-windowcy)+windowcy+d(windowcy)" acty="-4.1568627450980395" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P14)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P14)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Plumb name="perp1" n="3" color="2" hidden="super" point="P13" line="l4" valid="true">Perpendiculaire passant par P13 à l4</Plumb>
<Intersection name="I1" n="4" color="2" hidden="super" showname="true" first="l4" second="perp1" shape="circle">Intersection entre l4 et perp1</Intersection>
<Point name="P3" n="5" color="5" hidden="super" showname="true" x="if(I1,x(I1),x(P13)+1)" actx="6.0" y="if(I1,y(I1),y(P13))" acty="0.0" shape="circle" fixed="true">Point à &quot;if(I1,x(I1),x(P13)+1)&quot;, &quot;if(I1,y(I1),y(P13))&quot;</Point>
<Text name="Text1" n="6" color="2" target="true" ctag0="green" cexpr0="1" x="if(d(P13,P3)~=0,x(P14)+15/pixel,invalid)" y="y(P14)+13/pixel" fixed="true">Le point est sur l&apos;objet</Text>
<Text name="Text2" n="7" color="2" target="true" ctag0="red" cexpr0="1" x="if(!(d(P13,P3)~=0),x(P14)+15/pixel,invalid)" y="y(P14)+13/pixel" fixed="true">Le point n&apos;est pas sur l&apos;objet</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_conf" showduplicates="true">
<Parameter name="M">M</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="P3">P3</Parameter>
<Objects>
<Point name="A" n="0" mainparameter="true" x="3.0" y="2.0">Point à 3.0, 2.0</Point>
<Point name="M" n="1" mainparameter="true" x="3.0" y="2.0">Point à 3.0, 2.0</Point>
<Point name="P3" n="2" color="2" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P3)-windowcx)+windowcx+d(windowcx)" actx="-4.052287581699346" y="(windoww/(windoww-d(windoww)))*(y(P3)-windowcy)+windowcy+d(windowcy)" acty="-1.7908496732026142" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P3)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P3)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="3" target="true" ctag0="green" cexpr0="1" x="if(d(M,A)~=0,x(P3)+15/pixel,invalid)" y="y(P3)+13/pixel" fixed="true">Les points sont confondus</Text>
<Text name="Text2" n="4" target="true" ctag0="red" cexpr0="1" x="if(!(d(M,A)~=0),x(P3)+15/pixel,invalid)" y="y(P3)+13/pixel" fixed="true">Les points ne sont pas confondus</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/function_u" showduplicates="true">
<Parameter name="A">A</Parameter>
<Objects>
<Point name="A" n="0" color="2" hidden="true" mainparameter="true" target="true" ctag0="showname" cexpr0="0" ctag1="hidden" cexpr1="1" x="(windoww/(windoww-d(windoww)))*(x(A)-windowcx)+windowcx+d(windowcx)" actx="0.0607594936708864" y="(windoww/(windoww-d(windoww)))*(y(A)-windowcy)+windowcy+d(windowcy)" acty="0.8810126582278475" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(A)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(A)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Function name="f1" n="1" type="thick" target="true" ctag0="showname" cexpr0="1" ctag1="showvalue" cexpr1="1" f="0" x="x(A)" y="y(A)" fixed="true" var="x">f1(x)=0</Function>
</Objects>
</Macro>
</CaR>
