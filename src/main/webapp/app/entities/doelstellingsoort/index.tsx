import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Doelstellingsoort from './doelstellingsoort';
import DoelstellingsoortDetail from './doelstellingsoort-detail';
import DoelstellingsoortUpdate from './doelstellingsoort-update';
import DoelstellingsoortDeleteDialog from './doelstellingsoort-delete-dialog';

const DoelstellingsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Doelstellingsoort />} />
    <Route path="new" element={<DoelstellingsoortUpdate />} />
    <Route path=":id">
      <Route index element={<DoelstellingsoortDetail />} />
      <Route path="edit" element={<DoelstellingsoortUpdate />} />
      <Route path="delete" element={<DoelstellingsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoelstellingsoortRoutes;
