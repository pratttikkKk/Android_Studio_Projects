const mongoose = require('mongoose');

const NoteSchema = new mongoose.Schema(
  {
    text: { type: String, required: true, trim: true }
  },
  { timestamps: true }
);

module.exports = mongoose.model('Note', NoteSchema);
