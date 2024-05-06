import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Functioneelgebied from './functioneelgebied';
import FunctioneelgebiedDetail from './functioneelgebied-detail';
import FunctioneelgebiedUpdate from './functioneelgebied-update';
import FunctioneelgebiedDeleteDialog from './functioneelgebied-delete-dialog';

const FunctioneelgebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Functioneelgebied />} />
    <Route path="new" element={<FunctioneelgebiedUpdate />} />
    <Route path=":id">
      <Route index element={<FunctioneelgebiedDetail />} />
      <Route path="edit" element={<FunctioneelgebiedUpdate />} />
      <Route path="delete" element={<FunctioneelgebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FunctioneelgebiedRoutes;
