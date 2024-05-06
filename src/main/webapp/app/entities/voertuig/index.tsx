import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Voertuig from './voertuig';
import VoertuigDetail from './voertuig-detail';
import VoertuigUpdate from './voertuig-update';
import VoertuigDeleteDialog from './voertuig-delete-dialog';

const VoertuigRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Voertuig />} />
    <Route path="new" element={<VoertuigUpdate />} />
    <Route path=":id">
      <Route index element={<VoertuigDetail />} />
      <Route path="edit" element={<VoertuigUpdate />} />
      <Route path="delete" element={<VoertuigDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VoertuigRoutes;
