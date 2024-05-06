import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Organisatorischeeenheid from './organisatorischeeenheid';
import OrganisatorischeeenheidDetail from './organisatorischeeenheid-detail';
import OrganisatorischeeenheidUpdate from './organisatorischeeenheid-update';
import OrganisatorischeeenheidDeleteDialog from './organisatorischeeenheid-delete-dialog';

const OrganisatorischeeenheidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Organisatorischeeenheid />} />
    <Route path="new" element={<OrganisatorischeeenheidUpdate />} />
    <Route path=":id">
      <Route index element={<OrganisatorischeeenheidDetail />} />
      <Route path="edit" element={<OrganisatorischeeenheidUpdate />} />
      <Route path="delete" element={<OrganisatorischeeenheidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrganisatorischeeenheidRoutes;
