import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wozbelang from './wozbelang';
import WozbelangDetail from './wozbelang-detail';
import WozbelangUpdate from './wozbelang-update';
import WozbelangDeleteDialog from './wozbelang-delete-dialog';

const WozbelangRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wozbelang />} />
    <Route path="new" element={<WozbelangUpdate />} />
    <Route path=":id">
      <Route index element={<WozbelangDetail />} />
      <Route path="edit" element={<WozbelangUpdate />} />
      <Route path="delete" element={<WozbelangDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WozbelangRoutes;
