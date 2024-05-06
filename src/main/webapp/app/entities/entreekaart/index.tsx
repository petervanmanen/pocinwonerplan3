import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Entreekaart from './entreekaart';
import EntreekaartDetail from './entreekaart-detail';
import EntreekaartUpdate from './entreekaart-update';
import EntreekaartDeleteDialog from './entreekaart-delete-dialog';

const EntreekaartRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Entreekaart />} />
    <Route path="new" element={<EntreekaartUpdate />} />
    <Route path=":id">
      <Route index element={<EntreekaartDetail />} />
      <Route path="edit" element={<EntreekaartUpdate />} />
      <Route path="delete" element={<EntreekaartDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EntreekaartRoutes;
