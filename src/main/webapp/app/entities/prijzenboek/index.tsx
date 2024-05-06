import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Prijzenboek from './prijzenboek';
import PrijzenboekDetail from './prijzenboek-detail';
import PrijzenboekUpdate from './prijzenboek-update';
import PrijzenboekDeleteDialog from './prijzenboek-delete-dialog';

const PrijzenboekRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Prijzenboek />} />
    <Route path="new" element={<PrijzenboekUpdate />} />
    <Route path=":id">
      <Route index element={<PrijzenboekDetail />} />
      <Route path="edit" element={<PrijzenboekUpdate />} />
      <Route path="delete" element={<PrijzenboekDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PrijzenboekRoutes;
