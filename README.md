# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### 2023.10.21.
### CardWise
### Nemes Lili - (GLJD1D)
### lili.nemes@edu.bme.hu
### Laborvezető: Kövesdán Gábor

## Bemutatás

Az alkalmazás segítségével "tanulós" kártyákat lehet létrehozni, illetve ezek segítségével, interaktívan lehet tanulni, mely a legtöbb ember számára sokkal eredményesebb, mint az anyag passzív, újra és újra átolvasással történő tanulása. Célközönség bárki, aki egy adott témát ilyen módon szeretne megtanulni de nem szeretne papír alapú kártyákat létrehozni.

## Főbb funkciók

Az alkalmazás megnyitásakor egy splash screen majd egy login képernyő fogadja a felhasználót. A register gomb megnyomásásra egy dialógusablakban lehet regisztrálni, azaz új felhasználónevet és jelszót megadni. A belépés utáni képernyőn a felhasználó két menüpontból választhat: az egyik a My Decks, a másik pedig a My Stats. A My Stats menüpontban a felhasználót egy kalendár és különböző diagrammok fogadják, melyen látszik az elmúlt hónap aktivitása. Az apphoz egy widget is fog tartozni, amely a felhasználó streakjét, vagyis hogy hány napja használja kihagyás nélkül az appot mutatja meg. A My Decks menüpontban jelennek meg lista szerűen a felhasználó által korábban elkészített kártyacsomagok illetve egy gomb, melyre kattintva új Decket lehet létrehozni.Új Deck létrehozásakor meg kell adni egy dialógusablakban a kártyapakli címét, majd a következő oldalon szintén egy plusz gombra kattintva lehet dialógusablakban egy kártya két oldalának tartalmát megadni. Az elkészült kártyák ebben a nézetben  kártya formában jelennek majd meg, az elem oldalán található törlés és módosítás gombokkal lehet változtatásokat végezni rajtuk. Ha kész a pakli akkor a kész gomb megnyomásával lehet visszakerülni a My Decks menüpontba. Innen egy már kész paklira kattintva a pakli tartalma az előzőhez nagyon hasonló, listaszerű formátumban jelenik meg, azonban a képernyőn balra húzással egy új nézet jelenik meg, melyen a megtanult, ismételendő és nem is látott kártyákról készült chart látható, illetve három gomb: learn, delete és set deadline. A delete törli az egész kártyapaklit, a set deadlinennal be lehet állítani, hogy meddig kell megtanulni a kártyák tartalmát, és addig az alkalmazás minden nap egy, a felhasználó által beállítható időpontban értesítést küld a felhasználónak ha az még nem nyitotta meg ezt a paklit tanulás céljából. A learn lenyomásának hatására aktiválódó nézetben egy sorsolt kártyán látható kérdés lesz látható, melyre a felhasználónak választ kell adnia. A felhasználó ezután megtekintheti a helyes választ a kérdésre (mely animáció segítségével fog úgy kinézni, mintha a kártya másik oldalára fordulna) és megadhatja, hogy nem tudta, könnyű, közepes vagy nehéz volt ez a kérdés, illetve hogy szeretne-e tovább tanulni (ekkor új kérdés lesz látható), vagy abbahagyná a tanulást és kilépne a set menüjébe.



## Választott technológiák:

- UI
- Fragmentek és DialogFragmentek
- Splah screen
- Widget
- RecyclerView
- Perzisztens adattárolás
- Chartok
- Notification
- ViewPager
- Animációk
- CardView

