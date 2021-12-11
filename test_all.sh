make cleanBuild
for f in lattests/good/*.lat; do
  echo "=========================================="
  echo "Processing $f file.."
  echo "    --------compiler output:--------"
  ./compiler "$f"
  echo "=========================================="
  echo
done