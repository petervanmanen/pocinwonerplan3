import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Buurt from './buurt';
import BuurtDetail from './buurt-detail';
import BuurtUpdate from './buurt-update';
import BuurtDeleteDialog from './buurt-delete-dialog';

const BuurtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Buurt />} />
    <Route path="new" element={<BuurtUpdate />} />
    <Route path=":id">
      <Route index element={<BuurtDetail />} />
      <Route path="edit" element={<BuurtUpdate />} />
      <Route path="delete" element={<BuurtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BuurtRoutes;
