import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verhuurbaareenheid from './verhuurbaareenheid';
import VerhuurbaareenheidDetail from './verhuurbaareenheid-detail';
import VerhuurbaareenheidUpdate from './verhuurbaareenheid-update';
import VerhuurbaareenheidDeleteDialog from './verhuurbaareenheid-delete-dialog';

const VerhuurbaareenheidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verhuurbaareenheid />} />
    <Route path="new" element={<VerhuurbaareenheidUpdate />} />
    <Route path=":id">
      <Route index element={<VerhuurbaareenheidDetail />} />
      <Route path="edit" element={<VerhuurbaareenheidUpdate />} />
      <Route path="delete" element={<VerhuurbaareenheidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerhuurbaareenheidRoutes;
