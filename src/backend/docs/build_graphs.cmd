@REM We use Graphviz to compile the .dot files to images.
@REM There's a plugin available for IJ but it's close to unusable.a

dot -Tpng services_graph.dot > services_graph.png

