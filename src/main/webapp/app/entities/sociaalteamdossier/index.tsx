import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sociaalteamdossier from './sociaalteamdossier';
import SociaalteamdossierDetail from './sociaalteamdossier-detail';
import SociaalteamdossierUpdate from './sociaalteamdossier-update';
import SociaalteamdossierDeleteDialog from './sociaalteamdossier-delete-dialog';

const SociaalteamdossierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sociaalteamdossier />} />
    <Route path="new" element={<SociaalteamdossierUpdate />} />
    <Route path=":id">
      <Route index element={<SociaalteamdossierDetail />} />
      <Route path="edit" element={<SociaalteamdossierUpdate />} />
      <Route path="delete" element={<SociaalteamdossierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SociaalteamdossierRoutes;
