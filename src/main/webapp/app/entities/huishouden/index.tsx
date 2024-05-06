import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Huishouden from './huishouden';
import HuishoudenDetail from './huishouden-detail';
import HuishoudenUpdate from './huishouden-update';
import HuishoudenDeleteDialog from './huishouden-delete-dialog';

const HuishoudenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Huishouden />} />
    <Route path="new" element={<HuishoudenUpdate />} />
    <Route path=":id">
      <Route index element={<HuishoudenDetail />} />
      <Route path="edit" element={<HuishoudenUpdate />} />
      <Route path="delete" element={<HuishoudenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HuishoudenRoutes;
