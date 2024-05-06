import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Productofdienst from './productofdienst';
import ProductofdienstDetail from './productofdienst-detail';
import ProductofdienstUpdate from './productofdienst-update';
import ProductofdienstDeleteDialog from './productofdienst-delete-dialog';

const ProductofdienstRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Productofdienst />} />
    <Route path="new" element={<ProductofdienstUpdate />} />
    <Route path=":id">
      <Route index element={<ProductofdienstDetail />} />
      <Route path="edit" element={<ProductofdienstUpdate />} />
      <Route path="delete" element={<ProductofdienstDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProductofdienstRoutes;
