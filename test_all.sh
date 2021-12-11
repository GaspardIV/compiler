make cleanBuild
for f in lattests/bad/*.lat; do
  echo "=========================================="
  echo "Processing $f file.."
  echo "    --------compiler output:--------"
  ./compiler "$f"
  echo "=========================================="
  echo
done