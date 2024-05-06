import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Auteur from './auteur';
import AuteurDetail from './auteur-detail';
import AuteurUpdate from './auteur-update';
import AuteurDeleteDialog from './auteur-delete-dialog';

const AuteurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Auteur />} />
    <Route path="new" element={<AuteurUpdate />} />
    <Route path=":id">
      <Route index element={<AuteurDetail />} />
      <Route path="edit" element={<AuteurUpdate />} />
      <Route path="delete" element={<AuteurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AuteurRoutes;
