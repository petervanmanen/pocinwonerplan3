import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Speelterrein from './speelterrein';
import SpeelterreinDetail from './speelterrein-detail';
import SpeelterreinUpdate from './speelterrein-update';
import SpeelterreinDeleteDialog from './speelterrein-delete-dialog';

const SpeelterreinRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Speelterrein />} />
    <Route path="new" element={<SpeelterreinUpdate />} />
    <Route path=":id">
      <Route index element={<SpeelterreinDetail />} />
      <Route path="edit" element={<SpeelterreinUpdate />} />
      <Route path="delete" element={<SpeelterreinDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SpeelterreinRoutes;
