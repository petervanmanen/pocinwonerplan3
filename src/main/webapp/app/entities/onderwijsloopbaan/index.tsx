import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onderwijsloopbaan from './onderwijsloopbaan';
import OnderwijsloopbaanDetail from './onderwijsloopbaan-detail';
import OnderwijsloopbaanUpdate from './onderwijsloopbaan-update';
import OnderwijsloopbaanDeleteDialog from './onderwijsloopbaan-delete-dialog';

const OnderwijsloopbaanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onderwijsloopbaan />} />
    <Route path="new" element={<OnderwijsloopbaanUpdate />} />
    <Route path=":id">
      <Route index element={<OnderwijsloopbaanDetail />} />
      <Route path="edit" element={<OnderwijsloopbaanUpdate />} />
      <Route path="delete" element={<OnderwijsloopbaanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnderwijsloopbaanRoutes;
