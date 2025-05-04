package app.lilaverse.astrostatsandroid

import kotlin.math.abs

sealed class Aspect(val remainder: Double) {
    class Conjunction(r: Double) : Aspect(r)
    class Semisextile(r: Double) : Aspect(r)
    class Semisquare(r: Double) : Aspect(r)
    class Sextile(r: Double) : Aspect(r)
    class Square(r: Double) : Aspect(r)
    class Trine(r: Double) : Aspect(r)
    class Sesquisquare(r: Double) : Aspect(r)
    class Inconjunction(r: Double) : Aspect(r)
    class Opposition(r: Double) : Aspect(r)
    class Parallel(r: Double) : Aspect(r)

    companion object {
        fun detect(a: Double, b: Double, orb: Double): Aspect? {
            val delta = abs(b - a).let { if (it > 180) 360 - it else it }

            return when {
                delta in (0.0 - orb)..(0.0 + orb) -> Conjunction(delta)
                delta in (30.0 - orb)..(30.0 + orb) -> Semisextile(delta - 30)
                delta in (45.0 - orb)..(45.0 + orb) -> Semisquare(delta - 45)
                delta in (60.0 - orb)..(60.0 + orb) -> Sextile(delta - 60)
                delta in (90.0 - orb)..(90.0 + orb) -> Square(delta - 90)
                delta in (120.0 - orb)..(120.0 + orb) -> Trine(delta - 120)
                delta in (135.0 - orb)..(135.0 + orb) -> Sesquisquare(delta - 135)
                delta in (150.0 - orb)..(150.0 + orb) -> Inconjunction(delta - 150)
                delta in (180.0 - orb)..(180.0 + orb) -> Opposition(delta - 180)
                delta in (-1.0)..(1.0) -> Parallel(delta) // for declination
                else -> null
            }
        }
    }
}
