import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Begroting from './begroting';
import BegrotingDetail from './begroting-detail';
import BegrotingUpdate from './begroting-update';
import BegrotingDeleteDialog from './begroting-delete-dialog';

const BegrotingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Begroting />} />
    <Route path="new" element={<BegrotingUpdate />} />
    <Route path=":id">
      <Route index element={<BegrotingDetail />} />
      <Route path="edit" element={<BegrotingUpdate />} />
      <Route path="delete" element={<BegrotingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BegrotingRoutes;
