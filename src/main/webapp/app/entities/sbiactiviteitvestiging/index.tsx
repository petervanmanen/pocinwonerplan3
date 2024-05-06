import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sbiactiviteitvestiging from './sbiactiviteitvestiging';
import SbiactiviteitvestigingDetail from './sbiactiviteitvestiging-detail';
import SbiactiviteitvestigingUpdate from './sbiactiviteitvestiging-update';
import SbiactiviteitvestigingDeleteDialog from './sbiactiviteitvestiging-delete-dialog';

const SbiactiviteitvestigingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sbiactiviteitvestiging />} />
    <Route path="new" element={<SbiactiviteitvestigingUpdate />} />
    <Route path=":id">
      <Route index element={<SbiactiviteitvestigingDetail />} />
      <Route path="edit" element={<SbiactiviteitvestigingUpdate />} />
      <Route path="delete" element={<SbiactiviteitvestigingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SbiactiviteitvestigingRoutes;
