import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Productieeenheid from './productieeenheid';
import ProductieeenheidDetail from './productieeenheid-detail';
import ProductieeenheidUpdate from './productieeenheid-update';
import ProductieeenheidDeleteDialog from './productieeenheid-delete-dialog';

const ProductieeenheidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Productieeenheid />} />
    <Route path="new" element={<ProductieeenheidUpdate />} />
    <Route path=":id">
      <Route index element={<ProductieeenheidDetail />} />
      <Route path="edit" element={<ProductieeenheidUpdate />} />
      <Route path="delete" element={<ProductieeenheidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProductieeenheidRoutes;
