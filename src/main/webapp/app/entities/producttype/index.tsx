import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Producttype from './producttype';
import ProducttypeDetail from './producttype-detail';
import ProducttypeUpdate from './producttype-update';
import ProducttypeDeleteDialog from './producttype-delete-dialog';

const ProducttypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Producttype />} />
    <Route path="new" element={<ProducttypeUpdate />} />
    <Route path=":id">
      <Route index element={<ProducttypeDetail />} />
      <Route path="edit" element={<ProducttypeUpdate />} />
      <Route path="delete" element={<ProducttypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProducttypeRoutes;
