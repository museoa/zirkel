<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="Complements/Angle Bisector as Ray">
<Parameter name="P1">Point on first leg of angle</Parameter>
<Parameter name="P2">Vertex of angle</Parameter>
<Parameter name="P3">Point on second leg of angle</Parameter>
<Comment>
<P>Angle bisector of angle P1P2P3 as ray.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.100000000000001" y="4.26">Point à -4.1, 4.26</Point>
<Point name="P2" n="1" mainparameter="true" x="-0.5250000000000008" y="-2.6875">Point à -0.53, -2.69</Point>
<Point name="P3" n="2" mainparameter="true" x="3.425000000000001" y="2.7875">Point à 3.43, 2.79</Point>
<Ray name="r1" n="3" hidden="super" from="P2" to="P1">Strahl von P2 in Richtung P1</Ray>
<Circle name="k1" n="4" hidden="super" through="P3" midpoint="P2" acute="true">Kreis um P2 durch P3</Circle>
<Intersection name="S1" n="5" hidden="super" first="r1" second="k1" which="first">Schnitt zwischen r1 und k1</Intersection>
<Midpoint name="M1" n="6" hidden="super" first="S1" second="P3">Mitte zwischen S1 und P3</Midpoint>
<Ray name="r2" n="7" target="true" from="P2" to="M1">Strahl von P2 in Richtung M1</Ray>
</Objects>
</Macro>
<Macro Name="Complements/Projection of Point to Line">
<Parameter name="g1">Line to project to</Parameter>
<Parameter name="P3">Projected point</Parameter>
<Comment>
<P>Projects P to the line g.</P>
</Comment>
<Objects>
<Line name="g1" n="0" mainparameter="true">???</Line>
<Point name="P3" n="1" mainparameter="true" x="1.675041876046901" y="2.46566164154104">Point à 1.68, 2.47</Point>
<Plumb name="l1" n="2" hidden="super" point="P3" line="g1" valid="true">Lot durch P3 zu g1</Plumb>
<Intersection name="S1" n="3" target="true" first="g1" second="l1">Schnitt zwischen g1 und l1</Intersection>
</Objects>
</Macro>
<Macro Name="Complements/Rotation">
<Parameter name="P1">Point on first leg of angle</Parameter>
<Parameter name="P2">Vertex of angle</Parameter>
<Parameter name="P3">Point on second leg of angle</Parameter>
<Parameter name="P4">Rotation center</Parameter>
<Parameter name="P5">Rotating point</Parameter>
<Comment>
<P>Rotate a point P around Q with angle ABC.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="2.8526100000000008" y="-3.271">Point à 2.85, -3.27</Point>
<Point name="P2" n="1" mainparameter="true" x="-3.689381933438986" y="-3.3217115689381935">Point à -3.69, -3.32</Point>
<Point name="P3" n="2" mainparameter="true" x="2.3454800000000002" y="-0.9128400000000001">Point à 2.35, -0.91</Point>
<Point name="P4" n="3" mainparameter="true" x="-3.942947702060222" y="0.6085578446909674">Point à -3.94, 0.61</Point>
<Point name="P5" n="4" mainparameter="true" x="1.2044400000000004" y="2.71315">Point à 1.2, 2.71</Point>
<Angle name="w1" n="5" color="1" hidden="super" first="P5" root="P4" fixed="a(P1,P2,P3)">Angle P5 - P4 de mesure a(P1,P2,P3)</Angle>
<Circle name="k1" n="6" color="1" hidden="super" through="P5" midpoint="P4" acute="true">Circle P4 through P5</Circle>
<Intersection name="S1" n="7" color="1" target="true" first="w1" second="k1" which="first">Intersection w1 and k1</Intersection>
</Objects>
</Macro>
<Macro Name="Complements/Rotation with angle">
<Parameter name="P1">Center of rotation</Parameter>
<Parameter name="P2">Rotating point</Parameter>
<Comment>
<P>Rotates a point P around Q with a given angle.</P>
</Comment>
<Objects>
<Point name="P1" n="0" mainparameter="true" x="-4.221870047543582" y="-0.48177496038034834">Point à -4.22, -0.48</Point>
<Point name="P2" n="1" mainparameter="true" x="2.244057052297941" y="0.4564183835182246">Point à 2.24, 0.46</Point>
<Angle name="w1" n="2" hidden="super" first="P2" root="P1" fixed="16">Angle P2 - P1 de mesure 16</Angle>
<Circle name="k1" n="3" hidden="super" through="P2" midpoint="P1" acute="true">Circle around P1 through P2</Circle>
<Intersection name="S1" n="4" target="true" first="w1" second="k1" which="first">Intersection of w1 and k1</Intersection>
</Objects>
<PromptFor object0="w1" prompt0="w1"/>
</Macro>
</CaR>
