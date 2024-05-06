import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subsidiecomponent from './subsidiecomponent';
import SubsidiecomponentDetail from './subsidiecomponent-detail';
import SubsidiecomponentUpdate from './subsidiecomponent-update';
import SubsidiecomponentDeleteDialog from './subsidiecomponent-delete-dialog';

const SubsidiecomponentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subsidiecomponent />} />
    <Route path="new" element={<SubsidiecomponentUpdate />} />
    <Route path=":id">
      <Route index element={<SubsidiecomponentDetail />} />
      <Route path="edit" element={<SubsidiecomponentUpdate />} />
      <Route path="delete" element={<SubsidiecomponentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubsidiecomponentRoutes;
