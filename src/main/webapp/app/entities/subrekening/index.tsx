import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subrekening from './subrekening';
import SubrekeningDetail from './subrekening-detail';
import SubrekeningUpdate from './subrekening-update';
import SubrekeningDeleteDialog from './subrekening-delete-dialog';

const SubrekeningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subrekening />} />
    <Route path="new" element={<SubrekeningUpdate />} />
    <Route path=":id">
      <Route index element={<SubrekeningDetail />} />
      <Route path="edit" element={<SubrekeningUpdate />} />
      <Route path="delete" element={<SubrekeningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubrekeningRoutes;
