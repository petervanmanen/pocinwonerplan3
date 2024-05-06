import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Haltverwijzing from './haltverwijzing';
import HaltverwijzingDetail from './haltverwijzing-detail';
import HaltverwijzingUpdate from './haltverwijzing-update';
import HaltverwijzingDeleteDialog from './haltverwijzing-delete-dialog';

const HaltverwijzingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Haltverwijzing />} />
    <Route path="new" element={<HaltverwijzingUpdate />} />
    <Route path=":id">
      <Route index element={<HaltverwijzingDetail />} />
      <Route path="edit" element={<HaltverwijzingUpdate />} />
      <Route path="delete" element={<HaltverwijzingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HaltverwijzingRoutes;
