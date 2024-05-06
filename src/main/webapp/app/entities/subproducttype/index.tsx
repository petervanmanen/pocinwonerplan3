import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subproducttype from './subproducttype';
import SubproducttypeDetail from './subproducttype-detail';
import SubproducttypeUpdate from './subproducttype-update';
import SubproducttypeDeleteDialog from './subproducttype-delete-dialog';

const SubproducttypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subproducttype />} />
    <Route path="new" element={<SubproducttypeUpdate />} />
    <Route path=":id">
      <Route index element={<SubproducttypeDetail />} />
      <Route path="edit" element={<SubproducttypeUpdate />} />
      <Route path="delete" element={<SubproducttypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubproducttypeRoutes;
