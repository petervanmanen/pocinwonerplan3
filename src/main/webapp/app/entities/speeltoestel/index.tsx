import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Speeltoestel from './speeltoestel';
import SpeeltoestelDetail from './speeltoestel-detail';
import SpeeltoestelUpdate from './speeltoestel-update';
import SpeeltoestelDeleteDialog from './speeltoestel-delete-dialog';

const SpeeltoestelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Speeltoestel />} />
    <Route path="new" element={<SpeeltoestelUpdate />} />
    <Route path=":id">
      <Route index element={<SpeeltoestelDetail />} />
      <Route path="edit" element={<SpeeltoestelUpdate />} />
      <Route path="delete" element={<SpeeltoestelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SpeeltoestelRoutes;
