import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Weginrichtingsobject from './weginrichtingsobject';
import WeginrichtingsobjectDetail from './weginrichtingsobject-detail';
import WeginrichtingsobjectUpdate from './weginrichtingsobject-update';
import WeginrichtingsobjectDeleteDialog from './weginrichtingsobject-delete-dialog';

const WeginrichtingsobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Weginrichtingsobject />} />
    <Route path="new" element={<WeginrichtingsobjectUpdate />} />
    <Route path=":id">
      <Route index element={<WeginrichtingsobjectDetail />} />
      <Route path="edit" element={<WeginrichtingsobjectUpdate />} />
      <Route path="delete" element={<WeginrichtingsobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WeginrichtingsobjectRoutes;
