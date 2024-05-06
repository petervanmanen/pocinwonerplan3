import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subsidie from './subsidie';
import SubsidieDetail from './subsidie-detail';
import SubsidieUpdate from './subsidie-update';
import SubsidieDeleteDialog from './subsidie-delete-dialog';

const SubsidieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subsidie />} />
    <Route path="new" element={<SubsidieUpdate />} />
    <Route path=":id">
      <Route index element={<SubsidieDetail />} />
      <Route path="edit" element={<SubsidieUpdate />} />
      <Route path="delete" element={<SubsidieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubsidieRoutes;
