import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Deelplanveld from './deelplanveld';
import DeelplanveldDetail from './deelplanveld-detail';
import DeelplanveldUpdate from './deelplanveld-update';
import DeelplanveldDeleteDialog from './deelplanveld-delete-dialog';

const DeelplanveldRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Deelplanveld />} />
    <Route path="new" element={<DeelplanveldUpdate />} />
    <Route path=":id">
      <Route index element={<DeelplanveldDetail />} />
      <Route path="edit" element={<DeelplanveldUpdate />} />
      <Route path="delete" element={<DeelplanveldDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DeelplanveldRoutes;
