import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Koppeling from './koppeling';
import KoppelingDetail from './koppeling-detail';
import KoppelingUpdate from './koppeling-update';
import KoppelingDeleteDialog from './koppeling-delete-dialog';

const KoppelingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Koppeling />} />
    <Route path="new" element={<KoppelingUpdate />} />
    <Route path=":id">
      <Route index element={<KoppelingDetail />} />
      <Route path="edit" element={<KoppelingUpdate />} />
      <Route path="delete" element={<KoppelingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KoppelingRoutes;
