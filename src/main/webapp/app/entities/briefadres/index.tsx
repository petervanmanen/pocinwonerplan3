import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Briefadres from './briefadres';
import BriefadresDetail from './briefadres-detail';
import BriefadresUpdate from './briefadres-update';
import BriefadresDeleteDialog from './briefadres-delete-dialog';

const BriefadresRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Briefadres />} />
    <Route path="new" element={<BriefadresUpdate />} />
    <Route path=":id">
      <Route index element={<BriefadresDetail />} />
      <Route path="edit" element={<BriefadresUpdate />} />
      <Route path="delete" element={<BriefadresDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BriefadresRoutes;
