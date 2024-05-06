import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Productsoort from './productsoort';
import ProductsoortDetail from './productsoort-detail';
import ProductsoortUpdate from './productsoort-update';
import ProductsoortDeleteDialog from './productsoort-delete-dialog';

const ProductsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Productsoort />} />
    <Route path="new" element={<ProductsoortUpdate />} />
    <Route path=":id">
      <Route index element={<ProductsoortDetail />} />
      <Route path="edit" element={<ProductsoortUpdate />} />
      <Route path="delete" element={<ProductsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProductsoortRoutes;
