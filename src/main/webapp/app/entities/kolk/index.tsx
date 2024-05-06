import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kolk from './kolk';
import KolkDetail from './kolk-detail';
import KolkUpdate from './kolk-update';
import KolkDeleteDialog from './kolk-delete-dialog';

const KolkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kolk />} />
    <Route path="new" element={<KolkUpdate />} />
    <Route path=":id">
      <Route index element={<KolkDetail />} />
      <Route path="edit" element={<KolkUpdate />} />
      <Route path="delete" element={<KolkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KolkRoutes;
