import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Functiehuis from './functiehuis';
import FunctiehuisDetail from './functiehuis-detail';
import FunctiehuisUpdate from './functiehuis-update';
import FunctiehuisDeleteDialog from './functiehuis-delete-dialog';

const FunctiehuisRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Functiehuis />} />
    <Route path="new" element={<FunctiehuisUpdate />} />
    <Route path=":id">
      <Route index element={<FunctiehuisDetail />} />
      <Route path="edit" element={<FunctiehuisUpdate />} />
      <Route path="delete" element={<FunctiehuisDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FunctiehuisRoutes;
