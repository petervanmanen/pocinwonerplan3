import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Dossier from './dossier';
import DossierDetail from './dossier-detail';
import DossierUpdate from './dossier-update';
import DossierDeleteDialog from './dossier-delete-dialog';

const DossierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Dossier />} />
    <Route path="new" element={<DossierUpdate />} />
    <Route path=":id">
      <Route index element={<DossierDetail />} />
      <Route path="edit" element={<DossierUpdate />} />
      <Route path="delete" element={<DossierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DossierRoutes;
