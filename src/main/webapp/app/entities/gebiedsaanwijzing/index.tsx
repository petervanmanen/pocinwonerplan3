import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gebiedsaanwijzing from './gebiedsaanwijzing';
import GebiedsaanwijzingDetail from './gebiedsaanwijzing-detail';
import GebiedsaanwijzingUpdate from './gebiedsaanwijzing-update';
import GebiedsaanwijzingDeleteDialog from './gebiedsaanwijzing-delete-dialog';

const GebiedsaanwijzingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gebiedsaanwijzing />} />
    <Route path="new" element={<GebiedsaanwijzingUpdate />} />
    <Route path=":id">
      <Route index element={<GebiedsaanwijzingDetail />} />
      <Route path="edit" element={<GebiedsaanwijzingUpdate />} />
      <Route path="delete" element={<GebiedsaanwijzingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GebiedsaanwijzingRoutes;
