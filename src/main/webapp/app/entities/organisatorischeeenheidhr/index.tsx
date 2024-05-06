import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Organisatorischeeenheidhr from './organisatorischeeenheidhr';
import OrganisatorischeeenheidhrDetail from './organisatorischeeenheidhr-detail';
import OrganisatorischeeenheidhrUpdate from './organisatorischeeenheidhr-update';
import OrganisatorischeeenheidhrDeleteDialog from './organisatorischeeenheidhr-delete-dialog';

const OrganisatorischeeenheidhrRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Organisatorischeeenheidhr />} />
    <Route path="new" element={<OrganisatorischeeenheidhrUpdate />} />
    <Route path=":id">
      <Route index element={<OrganisatorischeeenheidhrDetail />} />
      <Route path="edit" element={<OrganisatorischeeenheidhrUpdate />} />
      <Route path="delete" element={<OrganisatorischeeenheidhrDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrganisatorischeeenheidhrRoutes;
