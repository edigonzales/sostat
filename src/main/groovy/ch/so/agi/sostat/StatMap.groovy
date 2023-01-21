package ch.so.agi.sostat

import geoscript.carto.MapItem
import geoscript.carto.PageSize
import geoscript.carto.RectangleItem
import geoscript.geom.Bounds
import geoscript.layer.Layer
import geoscript.proj.Projection
import geoscript.workspace.GeoPackage
import geoscript.workspace.Workspace
import geoscript.render.Map
import geoscript.carto.CartoFactories
import java.awt.Color

class StatMap {

    void create() {
        Workspace workspace = new GeoPackage('src/main/resources/ch.so.agi.hoheitsgrenzen.gpkg')
        Layer gemeindegrenze = workspace.get("hoheitsgrenzen_gemeindegrenze_generalisiert")

        println(gemeindegrenze.bounds())

        Map map = new Map(
                width: 1800,
                height: 1200,
                layers: [gemeindegrenze],
                bounds: new Bounds(2574160,1211102,2660339,1265337, "EPSG:2056"),
                projection: new Projection("EPSG:2056")
        )

        File file = new File("map.png")
        file.withOutputStream { OutputStream outputStream ->

            //PageSize pageSize = PageSize.d_LANDSCAPE
            PageSize pageSize = new PageSize(842, 595)

            CartoFactories.findByName("png")
                    .create(pageSize)
                    .rectangle(new RectangleItem(0, 0, pageSize.width - 1, pageSize.height - 1)
                            .fillColor(Color.WHITE)
                    )
                    .map(new MapItem(20, 20, pageSize.width - 40, pageSize.height - 40).map(map))
                    .build(outputStream)

        }





    }




}
