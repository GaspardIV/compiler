make cleanBuild
for f in lattests/bad/*.lat; do
  echo "=========================================="
  echo "Processing $f file.."
  echo "    --------compiler output:--------"
  ./latc "$f"
  echo "=========================================="
  echo
done