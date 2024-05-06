import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Regeling from './regeling';
import RegelingDetail from './regeling-detail';
import RegelingUpdate from './regeling-update';
import RegelingDeleteDialog from './regeling-delete-dialog';

const RegelingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Regeling />} />
    <Route path="new" element={<RegelingUpdate />} />
    <Route path=":id">
      <Route index element={<RegelingDetail />} />
      <Route path="edit" element={<RegelingUpdate />} />
      <Route path="delete" element={<RegelingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RegelingRoutes;
