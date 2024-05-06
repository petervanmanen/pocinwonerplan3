import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Prijzenboekitem from './prijzenboekitem';
import PrijzenboekitemDetail from './prijzenboekitem-detail';
import PrijzenboekitemUpdate from './prijzenboekitem-update';
import PrijzenboekitemDeleteDialog from './prijzenboekitem-delete-dialog';

const PrijzenboekitemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Prijzenboekitem />} />
    <Route path="new" element={<PrijzenboekitemUpdate />} />
    <Route path=":id">
      <Route index element={<PrijzenboekitemDetail />} />
      <Route path="edit" element={<PrijzenboekitemUpdate />} />
      <Route path="delete" element={<PrijzenboekitemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PrijzenboekitemRoutes;
