import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Productgroep from './productgroep';
import ProductgroepDetail from './productgroep-detail';
import ProductgroepUpdate from './productgroep-update';
import ProductgroepDeleteDialog from './productgroep-delete-dialog';

const ProductgroepRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Productgroep />} />
    <Route path="new" element={<ProductgroepUpdate />} />
    <Route path=":id">
      <Route index element={<ProductgroepDetail />} />
      <Route path="edit" element={<ProductgroepUpdate />} />
      <Route path="delete" element={<ProductgroepDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProductgroepRoutes;
