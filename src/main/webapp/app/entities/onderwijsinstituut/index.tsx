import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onderwijsinstituut from './onderwijsinstituut';
import OnderwijsinstituutDetail from './onderwijsinstituut-detail';
import OnderwijsinstituutUpdate from './onderwijsinstituut-update';
import OnderwijsinstituutDeleteDialog from './onderwijsinstituut-delete-dialog';

const OnderwijsinstituutRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onderwijsinstituut />} />
    <Route path="new" element={<OnderwijsinstituutUpdate />} />
    <Route path=":id">
      <Route index element={<OnderwijsinstituutDetail />} />
      <Route path="edit" element={<OnderwijsinstituutUpdate />} />
      <Route path="delete" element={<OnderwijsinstituutDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnderwijsinstituutRoutes;
