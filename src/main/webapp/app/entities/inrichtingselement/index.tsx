import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inrichtingselement from './inrichtingselement';
import InrichtingselementDetail from './inrichtingselement-detail';
import InrichtingselementUpdate from './inrichtingselement-update';
import InrichtingselementDeleteDialog from './inrichtingselement-delete-dialog';

const InrichtingselementRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inrichtingselement />} />
    <Route path="new" element={<InrichtingselementUpdate />} />
    <Route path=":id">
      <Route index element={<InrichtingselementDetail />} />
      <Route path="edit" element={<InrichtingselementUpdate />} />
      <Route path="delete" element={<InrichtingselementDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InrichtingselementRoutes;
