import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subsidiebeschikking from './subsidiebeschikking';
import SubsidiebeschikkingDetail from './subsidiebeschikking-detail';
import SubsidiebeschikkingUpdate from './subsidiebeschikking-update';
import SubsidiebeschikkingDeleteDialog from './subsidiebeschikking-delete-dialog';

const SubsidiebeschikkingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subsidiebeschikking />} />
    <Route path="new" element={<SubsidiebeschikkingUpdate />} />
    <Route path=":id">
      <Route index element={<SubsidiebeschikkingDetail />} />
      <Route path="edit" element={<SubsidiebeschikkingUpdate />} />
      <Route path="delete" element={<SubsidiebeschikkingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubsidiebeschikkingRoutes;
