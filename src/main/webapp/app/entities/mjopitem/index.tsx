import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Mjopitem from './mjopitem';
import MjopitemDetail from './mjopitem-detail';
import MjopitemUpdate from './mjopitem-update';
import MjopitemDeleteDialog from './mjopitem-delete-dialog';

const MjopitemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Mjopitem />} />
    <Route path="new" element={<MjopitemUpdate />} />
    <Route path=":id">
      <Route index element={<MjopitemDetail />} />
      <Route path="edit" element={<MjopitemUpdate />} />
      <Route path="delete" element={<MjopitemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MjopitemRoutes;
